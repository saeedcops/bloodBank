package com.cops.bloodbankclone.utility;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.GeneralSpinnerAdapter;
import com.cops.bloodbankclone.data.model.generalResponce.GeneralResponce;
import com.cops.bloodbankclone.data.model.profile.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;

public class GeneralRequest {
    public static int govId=0;


/*

getCitySpinnerItems

 */

    public static void getCitySpinnerItems(Call<GeneralResponce> call,Activity activity, boolean edit, GeneralSpinnerAdapter governmentAdapter,
                                       GeneralSpinnerAdapter cityAdapter,Spinner spinnerCity,Spinner spinnerGovernment ) {

        call.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {

                try {
                    if (response.body().getStatus() == 1) {

                        if (!edit) {


                            AdapterView.OnItemSelectedListener governmentItemSelectedListener = new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position != 0) {

                                        getSpinnerData(getClient().getCity(position), spinnerCity, cityAdapter,
                                                response.body().getData().get(0).getId(), activity.getString(R.string.city));
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            };

                            getSpinnerData(getClient().getGovernment(), spinnerGovernment, governmentAdapter,
                                    response.body().getData().get(0).getId(), activity.getString(R.string.government), governmentItemSelectedListener);

                        }
                    }


                } catch (Exception e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

/*

getGovernorateSpinnerItems

 */

    public static void getGovernorateSpinnerItems(Call<GeneralResponce> call,Activity activity, boolean edit,
                                                GeneralSpinnerAdapter governmentAdapter, Spinner spinnerGovernment ) {

        call.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {

                try {

                    if (response.body().getStatus() == 1) {

                        if (!edit) {


                            getSpinnerData(getClient().getGovernment(), spinnerGovernment, governmentAdapter,
                                    response.body().getData().get(0).getId(), activity.getString(R.string.government));

                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    /*

getBloodTypeSpinnerItems

 */

    public static void getBloodTypeSpinnerItems(Call<GeneralResponce> call,Activity activity, boolean edit,
                                                GeneralSpinnerAdapter bloodTypeAdapter, Spinner spinnerBloodType ) {

        call.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {


                try {
                    if (response.body().getStatus() == 1) {

                        if (!edit) {


                            getSpinnerData(getClient().getBloodType(), spinnerBloodType, bloodTypeAdapter,
                                    response.body().getData().get(0).getId(), activity.getString(R.string.blood));


                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }



    public static void getSpinnerItems(Call<Profile> call,Activity activity, boolean edit, GeneralSpinnerAdapter governmentAdapter,GeneralSpinnerAdapter bloodTypeAdapter,
                                       Spinner spinnerBloodType,Spinner spinnerGovernment ) {

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {


                try {
                    if (response.body().getStatus() == 1) {

                        if (!edit) {


                            getSpinnerData(getClient().getBloodType(), spinnerBloodType, bloodTypeAdapter,
                                    response.body().getData().getClient().getBloodType().getId(), activity.getString(R.string.blood));

                            getSpinnerData(getClient().getGovernment(), spinnerGovernment, governmentAdapter,
                                    response.body().getData().getClient().getCity().getGovernorate().getId(), activity.getString(R.string.government));

                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    public static void getSpinnerItems(Call<Profile> call,Activity activity, boolean edit, GeneralSpinnerAdapter governmentAdapter,GeneralSpinnerAdapter bloodTypeAdapter,
                                 GeneralSpinnerAdapter cityAdapter,Spinner spinnerBloodType,Spinner spinnerCity,Spinner spinnerGovernment ) {

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {


                try {
                    if (response.body().getStatus() == 1) {

                        if (!edit) {


                            getSpinnerData(getClient().getBloodType(), spinnerBloodType, bloodTypeAdapter,
                                    response.body().getData().getClient().getBloodType().getId(), activity.getString(R.string.blood));
                            AdapterView.OnItemSelectedListener governmentItemSelectedListener = new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position != 0) {
                                        getSpinnerData(getClient().getCity(position), spinnerCity, cityAdapter,
                                                response.body().getData().getClient().getBloodType().getId(), activity.getString(R.string.city));
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            };

                            getSpinnerData(getClient().getGovernment(), spinnerGovernment, governmentAdapter,
                                    response.body().getData().getClient().getCity().getGovernorate().getId(), activity.getString(R.string.government), governmentItemSelectedListener);

                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }



    public static void getSpinnerData(Call<GeneralResponce> call, Spinner spinner, GeneralSpinnerAdapter adapter, Integer id, String hint, AdapterView.OnItemSelectedListener listener) {

        call.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {

                try {
                    adapter.setData(response.body().getData(),hint);
                    spinner.setAdapter(adapter);


                    for (int i = 0; i <adapter.generalResponceData.size() ; i++) {
                        if (adapter.generalResponceData.get(i).getId()==id) {
                            spinner.setSelection(adapter.selectedId);

                            break;
                        }

                    }

                    spinner.setOnItemSelectedListener(listener);
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });
    }

    public static void getSpinnerData(Call<GeneralResponce> call, Spinner spinner, GeneralSpinnerAdapter adapter, Integer id, String hint) {
        call.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {

                try {
                    adapter.setData(response.body().getData(),hint);
                    spinner.setAdapter(adapter);
                    spinner.setEnabled(true);

                    for (int i = 0; i <adapter.generalResponceData.size() ; i++) {

                        if (adapter.generalResponceData.get(i).getId()==id) {
                          //spinner.setSelection(i-1);
                            spinner.setSelection(adapter.selectedId);

                            break;
                        }

                    }

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });


    }
    public static void getSpinnerData(Activity activity,Call<GeneralResponce> call, Spinner spinner, GeneralSpinnerAdapter adapter, String hint) {
        call.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                        spinner.setSelection(adapter.selectedId);

                    }


                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });
    }
}
