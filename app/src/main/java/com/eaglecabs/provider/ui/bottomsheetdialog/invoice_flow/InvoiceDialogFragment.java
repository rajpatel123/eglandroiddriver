package com.eaglecabs.provider.ui.bottomsheetdialog.invoice_flow;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;

import com.eaglecabs.provider.MvpApplication;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseBottomSheetDialogFragment;
import com.eaglecabs.provider.data.network.model.Payment;
import com.eaglecabs.provider.data.network.model.RentalHourPackage;
import com.eaglecabs.provider.data.network.model.Request_;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eaglecabs.provider.base.BaseActivity.DATUM;
import static com.eaglecabs.provider.common.fcm.MyFirebaseMessagingService.INTENT_FILTER;

public class InvoiceDialogFragment extends BaseBottomSheetDialogFragment implements InvoiceDialogIView {

    @BindView(R.id.booking_id)
    TextView bookingId;
    @BindView(R.id.total_amount)
    TextView totalAmount;
    @BindView(R.id.payable_amount)
    TextView payableAmount;
    @BindView(R.id.payment_mode_layout)
    LinearLayout paymentModeLayout;
    @BindView(R.id.btnConfirmPayment)
    Button btnConfirmPayment;
    @BindView(R.id.lblPaymentType)
    TextView lblPaymentType;


    @BindView(R.id.lblPaymentTypeValue)
    TextView lblPaymentTypeValue;


    @BindView(R.id.noteLbl)
    TextView noteLbl;

    @BindView(R.id.total_distance)
    TextView totalDistance;
    @BindView(R.id.travel_time)
    TextView travelTime;
    @BindView(R.id.fixed)
    TextView fixed;
    @BindView(R.id.distance_fare)
    TextView distanceFare;
    @BindView(R.id.peek_hour_charges)
    TextView peekHourCharges;
    @BindView(R.id.night_fare)
    TextView nightFare;
    @BindView(R.id.tax)
    TextView tax;
    @BindView(R.id.discount)
    TextView discount;
    @BindView(R.id.discount_layout)
    LinearLayout discountLayout;


    @BindView(R.id.toll_tax)
    TextView toll_tax;
    @BindView(R.id.tollTaxLl)
    LinearLayout tollTaxLl;
    @BindView(R.id.tax2)
    TextView tax2;
    @BindView(R.id.commission)
    TextView commission;
    @BindView(R.id.tds)
    TextView tds;
    @BindView(R.id.provider_earnings)
    TextView providerEarnings;

    @BindView(R.id.layout_normal_flow)
    LinearLayout layout_normal_flow;
    @BindView(R.id.layout_rental_flow)
    LinearLayout layout_rental_flow;
    @BindView(R.id.rental_normal_price)
    TextView rentalNormalPrice;
    @BindView(R.id.rental_total_distance_km)
    TextView rentalTotalDistance;
    @BindView(R.id.rental_extra_hr_km_price)
    TextView rentalExtraHrKmPrice;

    @BindView(R.id.rental_extra_KM_price)
    TextView rental_extra_KM_price;

    @BindView(R.id.rental_extra_min_price)
    TextView rental_extra_min_price;


    @BindView(R.id.rental_travel_time)
    TextView rentalTravelTime;
    @BindView(R.id.rental_hours)
    TextView rentalHours;

    @BindView(R.id.layout_outstation_flow)
    LinearLayout layout_outstation_flow;
    @BindView(R.id.outstation_distance_travelled)
    TextView outstationDistanceTravelled;


    @BindView(R.id.travelledDistance)
    TextView travelledDistance;
    @BindView(R.id.outstation_distance_fare)
    TextView outstationDistanceFare;
    @BindView(R.id.outstation_driver_beta)
    TextView outstationDriverBeta;
    @BindView(R.id.outstation_round_single)
    TextView outstationRoundSingle;
    @BindView(R.id.outstation_no_of_days)
    TextView outstationNoOfDays;

    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.end_date)
    TextView endDate;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    InvoiceDialogPresenter<InvoiceDialogFragment> presenter = new InvoiceDialogPresenter<>();
    NumberFormat numberFormat;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invoice_dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void initView(View view) {
        setCancelable(false);
        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            View bottomSheetInternal = d.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        getDialog().setCanceledOnTouchOutside(false);
        ButterKnife.bind(this, view);
        presenter.attachView(this);
        numberFormat = MvpApplication.getNumberFormat();
        Request_ datum = DATUM;
        if (datum != null) {

            bookingId.setText(datum.getBookingId());
            startDate.setText("" + convertDateFormat(datum.getStartedAt()));
            endDate.setText("" + convertDateFormat(datum.getFinishedAt()));
            outstationDistanceTravelled.setText(String.valueOf(datum.getDistance() + " km"));

            if (datum.getTollTax() != null && datum.getTollTax() > 0) {
                toll_tax.setText(numberFormat.format(datum.getTollTax()));
                tollTaxLl.setVisibility(View.VISIBLE);
            } else {
                tollTaxLl.setVisibility(View.GONE);

            }
            travelTime.setText(getString(R.string._min, datum.getTravelTime()));
            if (datum.getPaymentMode().equalsIgnoreCase("CASH")) {
                noteLbl.setText("Note: Collect CASH PAYMENT");
            } else {
                noteLbl.setText("Note: Collect PAYTM PAYMENT");

            }
            lblPaymentTypeValue.setText(datum.getPaymentMode());
            Payment payment = datum.getPayment();
            if (payment != null) {
                totalDistance.setText(String.valueOf(payment.getDistance() + " km"));
                fixed.setText(numberFormat.format(payment.getFixed()));
                Double total = payment.getTotal() - payment.getTax();
                totalAmount.setText(numberFormat.format(total));
                peekHourCharges.setText(numberFormat.format(payment.getPeakPrice()));
                payableAmount.setText(numberFormat.format(payment.getPayable()));
                distanceFare.setText(numberFormat.format(0.00));
                rental_extra_KM_price.setText(numberFormat.format(payment.getRentalExtraKmPrice()));
                rental_extra_min_price.setText(numberFormat.format(payment.getRental_extra_minute_price()));
                rentalExtraHrKmPrice.setText(numberFormat.format(payment.getRentalExtraHrPrice()));
                tax.setText(numberFormat.format(payment.getTax()));
                tax2.setText(numberFormat.format(payment.getTax()));
                discountLayout.setVisibility(payment.getDiscount() > 0 ? View.VISIBLE : View.GONE);
                discount.setText(numberFormat.format(payment.getDiscount()));
                nightFare.setText(numberFormat.format(payment.getNightFare()));
                commission.setText(numberFormat.format(payment.getProviderCommission()));
                providerEarnings.setText(numberFormat.format(payment.getProviderPay()));

                //rental
                RentalHourPackage rentalHourPackage = datum.getRentalPackage();
                if (rentalHourPackage != null) {
                    rentalHours.setText(String.format("%s (%s Hrs)", getString(R.string.rental_normal_price), rentalHourPackage.getHour()));
                }
                rentalNormalPrice.setText(numberFormat.format(payment.getMinute()));
                rentalTravelTime.setText(getString(R.string._min, datum.getTravelTime()));
                rentalTotalDistance.setText(String.valueOf(datum.getDistance() + " km"));
                if (payment.getRentalExtraHrPrice() != null && payment.getRentalExtraKmPrice() != null)
                    rentalExtraHrKmPrice.setText(numberFormat.format(payment.getRentalExtraHrPrice() + payment.getRentalExtraKmPrice()));


                //outstation
                outstationDriverBeta.setText(String.format("(%s Days) %s", payment.getOutstationDays(), numberFormat.format(payment.getDriverBeta())));

                //outstationDriverBeta.setText(numberFormat.format(payment.getDriverBeta()));

                outstationDistanceFare.setText(numberFormat.format(0.00));
                travelledDistance.setText(String.valueOf(payment.getDistance() + " km"));
                outstationRoundSingle.setText(datum.getDay());

                if (payment.getOutstationDays() != null)
                    outstationNoOfDays.setText(String.format("%s Days", payment.getOutstationDays()));
                else
                    outstationNoOfDays.setText("-");

                String serviceReq = datum.getServiceRequired();
                switch (serviceReq) {
                    case "none":
                        layout_normal_flow.setVisibility(View.VISIBLE);
                        break;
                    case "rental":
                        layout_rental_flow.setVisibility(View.VISIBLE);
                        break;
                    case "outstation":
                        layout_outstation_flow.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

        }

        nestedScrollView.post(() -> nestedScrollView.fullScroll(View.FOCUS_DOWN));

    }


    @OnClick(R.id.btnConfirmPayment)
    public void onViewClicked() {

        if (DATUM != null) {
            Request_ datum = DATUM;
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", "COMPLETED");
            map.put("_method", "PATCH");
            showLoading();
            presenter.statusUpdate(map, datum.getId());

        }

    }


    @Override
    public void onSuccess(Object object) {

        dismissAllowingStateLoss();
        hideLoading();

        Intent intent = new Intent(INTENT_FILTER);
        activity().sendBroadcast(intent);

    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String convertDateFormat(String date) {
        String newDateString = null;

        if (TextUtils.isEmpty(date)) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            newDateString = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(dtf.format(now));


        } else {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            try {


                Date newDate = spf.parse(date);
                newDateString = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return newDateString;
    }
}
