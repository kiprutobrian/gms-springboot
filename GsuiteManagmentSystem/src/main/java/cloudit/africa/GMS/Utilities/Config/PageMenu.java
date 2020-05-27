package cloudit.africa.GMS.Utilities.Config;

public class PageMenu {

	public static String menuItemsView = "<aside\n" + 
			"		class=\"sidenav-main nav-expanded nav-lock nav-collapsible sidenav-light sidenav-active-square\" id=\"sidenav\">\n" + 
			"		<hr>\n" + 
			"		<div class=\"brand-sidebar\">\n" + 
			"\n" + 
			"			<h1 class=\"logo-wrapper\">\n" + 
			"				<a class=\"brand-logo darken-1\" href=\"/DashBoard\">GMS<span\n" + 
			"					class=\"logo-text hide-on-med-and-down\"> Portal</span></a><a\n" + 
			"					class=\"navbar-toggler\" href=\"#\"><i class=\"material-icons\">menu</i></a>\n" + 
			"			</h1>\n" + 
			"		</div>\n" + 
			"\n" + 
			"		<ul\n" + 
			"			class=\"sidenav sidenav-collapsible leftside-navigation collapsible sidenav-fixed menu-shadow\"\n" + 
			"			id=\"slide-out\" data-menu=\"menu-navigation\"\n" + 
			"			data-collapsible=\"menu-accordion\"\n" + 
			"			th:each=\"Services : ${servicesAcess}\">\n" + 
			"			<li class=\"active bold\"><a\n" + 
			"				class=\"collapsible-body waves-effect waves-cyan \" href=\"/DashBoard\"><i\n" + 
			"					class=\"material-icons\">dashboard</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">DashBoard</span></a></li>\n" + 
			"\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isUserManegment()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">person_outline</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">User Managment</span></a>\n" + 
			"				<div class=\"collapsible-body\" >\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\" >\n" + 
			"						<li th:if=\"${Services.isUserManegment()}\"><a\n" + 
			"							class=\"collapsible-body collapsible-header waves-effect waves-cyan\"\n" + 
			"							href=\"#\" data-i18n=\"\"><i class=\"material-icons\"><span\n" + 
			"									style=\"color: black\">G+</span></i><span>G-Suite Accounts</span></a>\n" + 
			"							<div class=\"collapsible-body\">\n" + 
			"								<ul class=\"collapsible\" data-collapsible=\"accordion\">\n" + 
			"									<li><a class=\"collapsible-body\" href=\"/usermanagment\"\n" + 
			"										data-i18n=\"\"><i class=\"material-icons\"></i><span>License</span></a></li>\n" + 
			"									<li><a class=\"collapsible-body\" href=\"/createUser\"\n" + 
			"										data-i18n=\"\"><i class=\"material-icons\"></i><span>Create\n" + 
			"												Account</span></a></li>\n" + 
			"\n" + 
			"								</ul>\n" + 
			"							</div></li>\n" + 
			"						<li th:if=\"${Services.isSetting()}\"><a\n" + 
			"							class=\"collapsible-body collapsible-header waves-effect waves-cyan\"\n" + 
			"							href=\"#\" data-i18n=\"\"><i class=\"material-icons\"><span\n" + 
			"									style=\"color: black\">GMS</span></i><span>GMS Accounts</span></a>\n" + 
			"							<div class=\"collapsible-body\">\n" + 
			"								<ul class=\"collapsible\" data-collapsible=\"accordion\">\n" + 
			"									<li><a class=\"collapsible-body\" href=\"/GMSSetting\"\n" + 
			"										data-i18n=\"\"><i class=\"material-icons\"></i><span>License</span></a>\n" + 
			"									</li>\n" + 
			"									<li><a class=\"collapsible-body\" href=\"/AdminControl\"\n" + 
			"										data-i18n=\"\"><i class=\"material-icons\"></i><span>Role\n" + 
			"												Access</span></a></li>\n" + 
			"								</ul>\n" + 
			"							</div></li>\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isSignature()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">credit_card</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Signature</span></a>\n" + 
			"				</li>\n" + 
			"\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isMarketingBranding()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">card_membership</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Marketing & Advertising</span></a>\n" + 
			"			</li>\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isCalenderApointment()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">date_range</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Calendar Appointments</span></a>\n" + 
			"			</li>\n" + 
			"\n" + 
			"			<li class=\"bold\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">mail_outline</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Email</span></a>\n" + 
			"				<div class=\"collapsible-body\">\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\">\n" + 
			"						<li th:if=\"${Services.isEmailAnalysis()}\"><a\n" + 
			"							class=\"collapsible-body\" href=\"/MailMetrix\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Mail Metrix</span></a></li>\n" + 
			"						<li th:if=\"${Services.isMaildelegation()}\"><a\n" + 
			"							class=\"collapsible-body\" href=\"/Delegation\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Delegation</span></a></li>\n" + 
			"\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isDriveAnalysis()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">folder_open</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Drive File & Folders </span></a>\n" + 
			"				<div class=\"collapsible-body\">\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\">\n" + 
			"						<li><a class=\"collapsible-body\" href=\"/DriveAnalysis\"\n" + 
			"							data-i18n=\"\"><i class=\"material-icons\"></i><span>Account\n" + 
			"									Drive</span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Domain Files</span></a></li>\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isDataMigration()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">crop_rotate</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Data Migration</span></a>\n" + 
			"				<div class=\"collapsible-body\">\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\">\n" + 
			"						<li><a class=\"collapsible-body\"\n" + 
			"							href=\"/DataMigration/BackMailBox\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>BackUp MailBox </span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\"\n" + 
			"							href=\"/DataMigration/RestorMailBox\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Restore MailBox </span></a></li>\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isReporting()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">description</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">DLP Reporting</span></a>\n" + 
			"				<div class=\"collapsible-body\">\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\">\n" + 
			"						<li><a class=\"collapsible-body\" href=\"/EmailReport\"\n" + 
			"							data-i18n=\"\"><i class=\"material-icons\"></i><span>G-mail</span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\" href=\"/DriveReport\"\n" + 
			"							data-i18n=\"\"><i class=\"material-icons\"></i><span>Drive</span></a></li>\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isSetting()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">devices_other</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Devices</span></a>\n" + 
			"				<div class=\"collapsible-body\">\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\">\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Mobile</span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Chrome</span></a></li>\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isRegistration()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">business</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Organization</span></a>\n" + 
			"				<div class=\"collapsible-body\">\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\">\n" + 
			"\n" + 
			"						<li th:if=\"${Services.isRegistration()}\"><a\n" + 
			"							class=\"collapsible-body\" th:href=\"@{/Registration/}+${userId}\"\n" + 
			"							data-i18n=\"\"><i class=\"material-icons\"></i><span>Registertion</span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Info</span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Package & Subscription</span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Account Billing</span></a></li>\n" + 
			"\n" + 
			"\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isSetting()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">settings</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Setting</span></a>\n" + 
			"				<div class=\"collapsible-body\">\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\">\n" + 
			"\n" + 
			"						<li th:if=\"${Services.isRegistration()}\"><a\n" + 
			"							class=\"collapsible-body\" th:href=\"@{/Settings}\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Settings</span></a></li>\n" + 
			"						\n" + 
			"\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isSetting()}\"><a\n" + 
			"				class=\"collapsible-header waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">android</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">GMS Apps</span></a>\n" + 
			"				<div class=\"collapsible-body\">\n" + 
			"					<ul class=\"collapsible collapsible-sub\"\n" + 
			"						data-collapsible=\"accordion\">\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Extensions</span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Mobile App</span></a></li>\n" + 
			"						<li><a class=\"collapsible-body\" href=\"#\" data-i18n=\"\"><i\n" + 
			"								class=\"material-icons\"></i><span>Addons</span></a></li>\n" + 
			"					</ul>\n" + 
			"				</div></li>\n" + 
			"			<li class=\"bold\" th:if=\"${Services.isSetting()}\"><a\n" + 
			"				class=\"collapsible-body waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">bug_report</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Reports & Logs</span></a></li>\n" + 
			"			<li class=\"bold\"><a\n" + 
			"				class=\"collapsible-body waves-effect waves-cyan \" href=\"#\"><i\n" + 
			"					class=\"material-icons\">help_outline</i><span class=\"menu-title\"\n" + 
			"					data-i18n=\"\">Help & Support</span></a></li>\n" + 
			"		</ul>\n" + 
			"		<div class=\"navigation-background\"></div>\n" + 
			"	</aside>\n" + 
			"	";
}
