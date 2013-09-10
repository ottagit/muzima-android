package com.muzima.adapters.forms;

import android.content.Context;
import android.util.Log;

import com.muzima.controller.FormController;
import com.muzima.model.CompleteForm;
import com.muzima.model.collections.CompleteForms;
import com.muzima.tasks.FormsAdapterBackgroundQueryTask;

public class CompleteFormsAdapter extends FormsAdapter {
    private static final String TAG = "CompleteFormsAdapter";

    public CompleteFormsAdapter(Context context, int textViewResourceId, FormController formController) {
        super(context, textViewResourceId, formController);
    }

    @Override
    public void reloadData() {
        new BackgroundQueryTask(this).execute();
    }

    public static class BackgroundQueryTask extends FormsAdapterBackgroundQueryTask<CompleteForm> {

        public BackgroundQueryTask(FormsAdapter formsAdapter) {
            super(formsAdapter);
        }

        @Override
        protected CompleteForms doInBackground(Void... voids) {
            CompleteForms completeForms = null;

            if (adapterWeakReference.get() != null) {
                try {
                    FormsAdapter formsAdapter = adapterWeakReference.get();
                    completeForms = ((CompleteFormsAdapter) formsAdapter).formController.getAllCompleteForms();

                    Log.i(TAG, "#Complete forms: " + completeForms.size());
                } catch (FormController.FormFetchException e) {
                    Log.w(TAG, "Exception occurred while fetching local forms " + e);
                }
            }

            return completeForms;
        }
    }
}
