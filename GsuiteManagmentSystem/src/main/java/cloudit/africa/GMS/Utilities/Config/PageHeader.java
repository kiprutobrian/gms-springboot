package cloudit.africa.GMS.Utilities.Config;

public class PageHeader {
	  static String appHeader_erroor = "";
	public static String appHeader = "<header class=\"dasboard-header page-topbar\" id=\"header\">\n" + 
			"		<div class=\"navbar navbar-fixed\">\n" + 
			"			<nav\n" + 
			"				class=\"nav\">\n" + 
			"				<div class=\"nav-wrapper\">\n" + 
			"					<div class=\"header-search-wrapper\">\n" + 
			"						<i class=\"material-icons\">search</i> <input\n" + 
			"							class=\"header-search-input z-depth-2\" type=\"text\" name=\"Search\"\n" + 
			"							placeholder=\"Explore GMS . . .\">\n" + 
			"					</div>\n" + 
			"					<ul class=\"navbar-list right\">\n" + 
			"						<li class=\"hide-on-large-only\"><a\n" + 
			"							class=\"waves-effect waves-block waves-light search-button\"\n" + 
			"							href=\"javascript:void(0);\"><i class=\"material-icons\">search</i></a></li>\n" + 
			"						<li class=\"notification\">\n" + 
			"						<i class=\"material-icons close\">close</i>\n" + 
			"						<a\n" + 
			"							class=\"waves-effect waves-block waves-light notification-button\"\n" + 
			"							href=\"javascript:void(0);\" data-target=\"notifications-dropdown\"><i\n" + 
			"								class=\"material-icons\">notifications_none<small\n" + 
			"									class=\"notification-badge\"></small></i></a></li>\n" + 
			"						<li class=\"profile\">\n" + 
			"						<a class=\"waves-effect waves-block waves-light profile-button\"\n" + 
			"							href=\"javascript:void(0);\" data-target=\"profile-dropdown\">\n" + 
			"							<span\n" + 
			"								class=\"avatar-status avatar-online\">\n" + 
			"								<img th:src=\"${image}\" alt=\"avatar\"><i></i>\n" + 
			"							</span>\n" + 
			"						</a>\n" + 
			"						<h3 class=\"name\">Tony Korir</h3>\n" + 
			"						 <p class=\"email\">tonny@dev.businesscom.dk</p>\n" + 
			"						 <p class=\"button\">Go to Profile</p>	\n" + 
			"						<div class=\"user\">\n" + 
			"							<h4 class=\"title\"><b>Notifications</b></h4>\n" + 
			"						    <div class=\"list\">\n" + 
			"						      <i class=\"material-icons\">mail</i> \n" + 
			"						       <div class=\"content\">\n" + 
			"						          <p class=\"text\">Your subscription has expired </p>\n" + 
			"						          <p class=\"time\">1 week ago</p>\n" + 
			"						       </div>\n" + 
			"						</div>\n" + 
			"						\n" + 
			"						</li>\n" + 
			"\n" + 
			"					</ul>\n" + 
			"\n" + 
			"\n" + 
			"					<ul class=\"dropdown-content\" id=\"notifications-dropdown\">\n" + 
			"						<li>\n" + 
			"							<h6>\n" + 
			"								NOTIFICATIONS<span class=\"new badge\" th:inline=\"text\">[[${notificationsize}]]</span>\n" + 
			"							</h6>\n" + 
			"						</li>\n" + 
			"						<li class=\"divider\"></li>\n" + 
			"\n" + 
			"						<li><a class=\"grey-text text-darken-2\" href=\"#!\"><span\n" + 
			"								class=\"material-icons icon-bg-circle cyan small\">add_shopping_cart</span>\n" + 
			"								A new order has been placed!</a> <time class=\"media-meta\"\n" + 
			"								datetime=\"2015-06-12T20:50:48+08:00\">2 hours ago</time></li>\n" + 
			"\n" + 
			"					</ul>\n" + 
			"					<ul class=\"dropdown-content\" id=\"profile-dropdown\">\n" + 
			"						<li><a class=\"grey-text text-darken-1\" href=\"#\"><i\n" + 
			"								class=\"material-icons\">person_outline</i> Profile</a></li>\n" + 
			"						<li><a class=\"grey-text text-darken-1\" href=\"#\"><i\n" + 
			"								class=\"material-icons\">chat_bubble_outline</i> Chat</a></li>\n" + 
			"						<li><a class=\"grey-text text-darken-1\" href=\"#\"><i\n" + 
			"								class=\"material-icons\">help_outline</i> Help</a></li>\n" + 
			"						<li class=\"divider\"></li>\n" + 
			"						<li><a class=\"grey-text text-darken-1\" href=\"#\"><i\n" + 
			"								class=\"material-icons\">keyboard_tab</i> Logout</a></li>\n" + 
			"					</ul>\n" + 
			"				</div>\n" + 
			"				<nav class=\"display-none search-sm\">\n" + 
			"					<div class=\"nav-wrapper\">\n" + 
			"						<form>\n" + 
			"							<div class=\"input-field\">\n" + 
			"								<input class=\"search-box-sm\" type=\"search\" required=\"\">\n" + 
			"								<label class=\"label-icon\" for=\"search\"><i\n" + 
			"									class=\"material-icons search-sm-icon\">search</i></label><i\n" + 
			"									class=\"material-icons search-sm-close\">close</i>\n" + 
			"							</div>\n" + 
			"						</form>\n" + 
			"					</div>\n" + 
			"				</nav>\n" + 
			"			</nav>\n" + 
			"		</div>\n" + 
			"	</header>\n" + 
			"	";
	
	
	
	
	
}