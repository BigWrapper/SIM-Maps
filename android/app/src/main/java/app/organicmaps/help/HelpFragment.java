package app.organicmaps.help;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import app.organicmaps.BuildConfig;
import app.organicmaps.Framework;
import app.organicmaps.R;
import app.organicmaps.base.BaseMwmFragment;
import app.organicmaps.util.DateUtils;
import app.organicmaps.util.SharingUtils;
import app.organicmaps.util.Utils;
import app.organicmaps.util.WindowInsetUtils.ScrollableContentInsetsListener;
import app.organicmaps.widget.DonationView;
import app.organicmaps.util.StringUtils;
import app.organicmaps.util.TextUtils;

public class HelpFragment extends BaseMwmFragment implements View.OnClickListener
{
  private String mDonateUrl;

  private TextView setupItem(@IdRes int id, boolean tint, @NonNull View frame)
  {
    final TextView view = frame.findViewById(id);
    view.setOnClickListener(this);
    if (tint) app.organicmaps.util.Graphics.tint(view);
    return view;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    mDonateUrl = Utils.getDonateUrl(requireContext());
    View root = inflater.inflate(R.layout.about, container, false);
    ((TextView) root.findViewById(R.id.version)).setText(BuildConfig.VERSION_NAME);
    final boolean isLandscape = getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE;
    final String dataVersion = DateUtils.getShortDateFormatter().format(Framework.nativeGetDataVersion());
    final TextView osmPresentationView = root.findViewById(R.id.osm_presentation);
    if (osmPresentationView != null) osmPresentationView.setText(getString(R.string.osm_presentation, dataVersion));
    setupItem(R.id.news, true, root);
    setupItem(R.id.web, true, root);
    setupItem(R.id.email, true, root);
    setupItem(R.id.github, true, root);
    setupItem(R.id.telegram, false, root);
    setupItem(R.id.instagram, false, root);
    setupItem(R.id.facebook, false, root);
    setupItem(R.id.twitter, true, root);
    setupItem(R.id.matrix, true, root);
    setupItem(R.id.mastodon, false, root);
    setupItem(R.id.openstreetmap, true, root);
    setupItem(R.id.faq, true, root);
    setupItem(R.id.report, isLandscape, root);
    final TextView supportUsView = root.findViewById(R.id.support_us);
    if (BuildConfig.FLAVOR.equals("google") && TextUtils.isEmpty(mDonateUrl))
      supportUsView.setVisibility(View.GONE);
    else
      setupItem(R.id.support_us, true, root);
    DonationView donationView = root.findViewById(R.id.donate);
    // HIDE ORGANIC MAPS DONATION - Show SIM Maps only
    donationView.setVisibility(View.GONE);
    donationView.setOnDonateClickListener(null);
    if (BuildConfig.REVIEW_URL.isEmpty())
      root.findViewById(R.id.rate).setVisibility(View.GONE);
    else
      setupItem(R.id.rate, true, root);
    setupItem(R.id.copyright, false, root);
    View termOfUseView = root.findViewById(R.id.term_of_use_link);
    View privacyPolicyView = root.findViewById(R.id.privacy_policy);
    termOfUseView.setOnClickListener(v -> Utils.openUrl(requireActivity(), getResources().getString(R.string.translated_om_term_of_use_link)));
    privacyPolicyView.setOnClickListener(v -> Utils.openUrl(requireActivity(), getResources().getString(R.string.translated_om_privacy_policy_link)));

    ViewCompat.setOnApplyWindowInsetsListener(root, new ScrollableContentInsetsListener(root));

    // SIM MAPS CUSTOM BUTTONS - CLICKABLE!
    View btnCapitec = root.findViewById(R.id.donate_button);
    if (btnCapitec != null) btnCapitec.setOnClickListener(v -> {
      ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
      cm.setPrimaryClip(ClipData.newPlainText("Capitec", "2450748535"));
      android.widget.Toast.makeText(getActivity(), "Capitec 2450748535 copied!", android.widget.Toast.LENGTH_SHORT).show();
      try {
        Intent launch = getActivity().getPackageManager().getLaunchIntentForPackage("com.capitec.bank");
        if (launch != null) startActivity(launch);
      } catch (Exception e) {}
    });
    View btnWa = root.findViewById(R.id.sim_whatsapp_button);
    if (btnWa != null) btnWa.setOnClickListener(v -> {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/27645263038?text=Hi%20Geandre%20I%20want%20to%20donate")));
    });
    View btnEmail = root.findViewById(R.id.sim_email_button);
    if (btnEmail != null) btnEmail.setOnClickListener(v -> {
      Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:drevanwyk1@gmail.com?subject=SIM Maps Donation"));
      startActivity(Intent.createChooser(email, "Email Geandre"));
    });

    return root;
  }

  @Override
  public void onClick(View v)
  {
    // Handle clicks for about items
    int id = v.getId();
    if (id == R.id.web) Utils.openUrl(requireActivity(), getString(R.string.website_url));
    else if (id == R.id.news) Utils.openUrl(requireActivity(), getString(R.string.news_url));
    else if (id == R.id.email) Utils.openUrl(requireActivity(), "mailto:" + getString(R.string.email_url));
    else if (id == R.id.github) Utils.openUrl(requireActivity(), getString(R.string.github_url));
  }
}
