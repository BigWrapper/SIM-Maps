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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import app.organicmaps.R;

public class HelpFragment extends Fragment
{
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View root = inflater.inflate(R.layout.about, container, false);

    // SIM MAPS CUSTOM BUTTONS - CLICKABLE!
    View btnCapitec = root.findViewById(R.id.donate_button);
    if (btnCapitec != null) {
      btnCapitec.setOnClickListener(v -> {
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("Capitec", "2450748535"));
        Toast.makeText(getActivity(), "Capitec 2450748535 copied!", Toast.LENGTH_SHORT).show();
        try {
          Intent launch = getActivity().getPackageManager().getLaunchIntentForPackage("com.capitec.bank");
          if (launch != null) startActivity(launch);
        } catch (Exception e) {}
      });
    }

    View btnWa = root.findViewById(R.id.sim_whatsapp_button);
    if (btnWa != null) {
      btnWa.setOnClickListener(v -> {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/27645263038?text=Hi%20Geandre%20I%20want%20to%20donate")));
      });
    }

    View btnEmail = root.findViewById(R.id.sim_email_button);
    if (btnEmail != null) {
      btnEmail.setOnClickListener(v -> {
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:drevanwyk1@gmail.com?subject=SIM Maps Donation"));
        startActivity(Intent.createChooser(email, "Email Geandre"));
      });
    }

    return root;
  }
}
