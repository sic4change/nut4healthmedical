package org.sic4change.nut4healthcentrotratamiento.ui.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.sic4change.nut4healthcentrotratamiento.R

@Composable
fun formatStatus(status: String) : String {
    if (status.equals("Normopeso") || status.equals("Poids Normal") || status.equals("وزن طبيعي")) {
        return stringResource(R.string.normopeso)
    } else if (status.equals("Desnutrición Aguda Moderada") || status.equals("Malnutrition Aiguë modérée")
        || status.equals("سوء التغذية الحاد المعتدل")){
        return stringResource(R.string.aguda_moderada)
    } else if (status.equals("Desnutrición Aguda Severa") || status.equals("Malnutrition Aiguë Sévère")
        || status.equals("سوء التغذية الحاد الوخيم")) {
        return stringResource(R.string.aguda_severa)
    } else {
        return stringResource(R.string.objetive_weight)
    }
}