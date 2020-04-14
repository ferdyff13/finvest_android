package com.invisee.finvest.crashreport;

import android.content.Context;
import android.support.annotation.NonNull;
import com.invisee.finvest.util.Constant;
import org.acra.config.ACRAConfiguration;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderFactory;


public class ACRAReportSenderFactory implements ReportSenderFactory {
    @NonNull
    @Override
    public ReportSender create(@NonNull Context context, @NonNull ACRAConfiguration config) {
        return new ACRAReportSender(context, Constant.mail, Constant.pass);
    }
}
