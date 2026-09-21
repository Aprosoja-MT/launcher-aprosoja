package app.lawnchair.data.usage

import android.content.Context

data class KnoxProfile(
    val groupName: String? = null,
    val site: String? = null,
    val siteCode: String? = null,
    val department: String? = null,
    val deviceTag: String? = null,
    val userTag: String? = null,
    val displayName: String? = null,
    val employeeNumber: String? = null,
    val phoneNumber: String? = null,
    val imei: String? = null,
    val iccid: String? = null,
    val carrier: String? = null,
    val deviceName: String? = null,
) {
    companion object {
        val Empty = KnoxProfile()
    }
}

object KnoxDeviceInfo {
    const val GROUP_NAME_RESTRICTION_KEY = "knox_group_name"
    const val SITE_RESTRICTION_KEY = "knox_site"
    const val SITE_CODE_RESTRICTION_KEY = "knox_site_code"
    const val DEPARTMENT_RESTRICTION_KEY = "knox_department"
    const val DEVICE_TAG_RESTRICTION_KEY = "knox_device_tag"
    const val USER_TAG_RESTRICTION_KEY = "knox_user_tag"
    const val DISPLAY_NAME_RESTRICTION_KEY = "knox_display_name"
    const val EMPLOYEE_NUMBER_RESTRICTION_KEY = "knox_employee_number"
    const val PHONE_NUMBER_RESTRICTION_KEY = "knox_phone_number"
    const val IMEI_RESTRICTION_KEY = "knox_imei"
    const val ICCID_RESTRICTION_KEY = "knox_iccid"
    const val CARRIER_RESTRICTION_KEY = "knox_carrier"
    const val DEVICE_NAME_RESTRICTION_KEY = "knox_device_name"

    fun read(context: Context): KnoxProfile = KnoxProfile(
        groupName = Restrictions.string(context, GROUP_NAME_RESTRICTION_KEY),
        site = Restrictions.string(context, SITE_RESTRICTION_KEY),
        siteCode = Restrictions.string(context, SITE_CODE_RESTRICTION_KEY),
        department = Restrictions.string(context, DEPARTMENT_RESTRICTION_KEY),
        deviceTag = Restrictions.string(context, DEVICE_TAG_RESTRICTION_KEY),
        userTag = Restrictions.string(context, USER_TAG_RESTRICTION_KEY),
        displayName = Restrictions.string(context, DISPLAY_NAME_RESTRICTION_KEY),
        employeeNumber = Restrictions.string(context, EMPLOYEE_NUMBER_RESTRICTION_KEY),
        phoneNumber = Restrictions.string(context, PHONE_NUMBER_RESTRICTION_KEY),
        imei = Restrictions.string(context, IMEI_RESTRICTION_KEY),
        iccid = Restrictions.string(context, ICCID_RESTRICTION_KEY),
        carrier = Restrictions.string(context, CARRIER_RESTRICTION_KEY),
        deviceName = Restrictions.string(context, DEVICE_NAME_RESTRICTION_KEY),
    )
}
