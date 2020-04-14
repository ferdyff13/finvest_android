package com.invisee.finvest.crashreport;
import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import java.util.Date;
import java.util.regex.Pattern;

public class ACRAReportSender implements ReportSender {

    private String emailUsername;
    private String emailPassword;
    private Context context;

    public ACRAReportSender(Context context, String emailUsername, String emailPassword) {
        super();
        this.context = context;
        this.emailUsername = emailUsername;
        this.emailPassword = emailPassword;
    }

    @Override
    public void send(@NonNull Context context, @NonNull CrashReportData errorContent) throws ReportSenderException {
        // Extract the required data out of the crash report.
        String reportBody = createCrashReport(errorContent);

        // instantiate the email sender
        GMailSender gMailSender = new GMailSender(emailUsername, emailPassword);

        try {
            // specify your recipients and send the email
            String title;
            title = "CRASH REPORT FINVEST";
            gMailSender.sendMail(title, reportBody, emailUsername, "ferdy.firmandika@invisee.com");
//            gMailSender.sendMail(title, reportBody, emailUsername, "gita.purnama@invisee.com, ferdy.firmandika@invisee.com, jimmy.wijaya@invisee.com");
        } catch (Exception e) {
            Log.d("Error Sending email", e.toString());
        }
    }


    private String createCrashReport(CrashReportData report) {
        // I've extracted only basic information.
        // U can add loads more data using the enum ReportField. See below.

        String emailLogin = PrefHelper.getString(PrefKey.EMAIL);
        String gmail = "GUEST";

        if (emailLogin.equalsIgnoreCase("")){
            int account_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS);
            if (account_permission == PackageManager.PERMISSION_GRANTED) {
                Pattern gmailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
                Account[] accounts = AccountManager.get(context).getAccounts();

                for (Account account : accounts) {
                    if (gmailPattern.matcher(account.name).matches()) {
                        gmail = account.name;
                        break;
                    }
                }
            }
        } else {
            gmail = emailLogin;
        }


        StringBuilder body = new StringBuilder();
        body
                .append("Date : " + new Date().toString())
                .append("\n")
                .append("Email : " + gmail)
                .append("\n")
                .append("Device : " + report.getProperty(ReportField.BRAND) + "-" + report.getProperty(ReportField.PHONE_MODEL))
                .append("\n")
                .append("Android Version : " + report.getProperty(ReportField.ANDROID_VERSION))
                .append("\n")
                .append("App Version : " + report.getProperty(ReportField.APP_VERSION_CODE))
                .append("\n")
                //.append("Build Time : " + GlobalController.getBuildTime())
                //.append("\n")
                .append("STACK TRACE : \n" + report.getProperty(ReportField.STACK_TRACE));
                return body.toString();
    }


}
