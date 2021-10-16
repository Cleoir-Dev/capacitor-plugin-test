package com.cleoir.plugins;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

@CapacitorPlugin(name = "cleoir")
public class cleoirPlugin extends Plugin {

    private cleoir implementation = new cleoir();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    private void requestReview(final CallbackContext callbackContext) {

        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        Activity currentActivity = getCurrentActivity();

        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                ReviewInfo reviewInfo = task.getResult();    

                if (currentActivity == null) {
                    rejectPromise("24", new Error("ACTIVITY_DOESN'T_EXIST"));
                    return;
                }

                Task<Void> flow = manager.launchReviewFlow(currentActivity, reviewInfo);
                flow.addOnCompleteListener(reviewFlow -> {
                    resolvePromise(reviewFlow.isSuccessful());
                });

            } else {
                @ReviewErrorCode int reviewErrorCode = ((TaskException) task.getException()).getErrorCode();
            }
        });
    }
}
