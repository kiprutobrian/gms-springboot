package cloudit.africa.GMS.SecurityConfigurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		UserDetailsService vc = auth.jdbcAuthentication().usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery).dataSource(dataSource).getUserDetailsService();

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		String[] resources = new String[] { "/", "/app-assets/js", "/app-assets/images/favicon/",
				"/app-assets/js/scripts/", "/app-assets/js/custom", "/app-assets/js/scripts/", "/app-assets/images/",
				"/app-assets/css/", "/app-assets/css/themes/vertical-modern-menu-template/", "app-assets/css/pages/",
				"/app-assets/css/custom/", "/app-assets/vendors/", "/app-assets/vendors/animate-css/",
				"/app-assets/vendors/chartist-js/", "/app-assets/vendors/chartjs/",
				"/app-assets/vendors/data-tables/css/", "/app-assets/vendors/data-tables/extensions/responsive/css/",
				"/app-assets/vendors/data-tables/extensions/responsive/js/", "/app-assets/vendors/data-tables/images/",
				"/app-assets/vendors/data-tables/js/", "/app-assets/vendors/css/", "/app-assets/vendors/js/",
				"/app-assets/vendors/flag-icon/css/", "/app-assets/vendors/flag-icon/flags/",
				"/app-assets/vendors/flag-icon/1x1/", "/app-assets/vendors/flag-icon/4x3/",
				"/app-assets/vendors/formatter/", "/app-assets/vendors/fullcalender/css/",
				"/app-assets/vendors/fullcalender/js", "/app-assets/vendors/fullcalender/lib/",
				"/app-assets/vendors/hover-effects/", "/app-assets/vendors/ionRangeSlider/css/",
				"/app-assets/vendors/ionRangeSlider/img", "/app-assets/vendors/ionRangeSlider/js",
				"/app-assets/vendors/jquery-cookies/", "/app-assets/vendors/jquery-jvectormap/",
				"/app-assets/vendors/jquery.nestable/", "/app-assets/vendors/magnific-popup/",
				"/app-assets/vendors/materialize-stepper/", "/app-assets/vendors/noUiSlider/",
				"/app-assets/vendors/sortable/", "/app-assets/vendors/sparkline/", "/app-assets/vendors/sweetalert/",
				"/app-assets/vendors/tinymce/", "/app-assets/vendors/translator/", "/app-assets/vendors/waypoints/",
				"/appointment/**", "/appointment/aicon/", "/appointment/aicon/demo-files/", "/appointment/aicon/fonts",
				"/appointment/css/",
				/*
				 * ===============================PUBLIC URL===========================================================
				 * ====================================================================================================
				 */
				"/gms-dashboard/**", "/gms-datatable/**", "/gms-datatablecheck**", "/gms-form**", "/app-assets/**",
				"/app-assets", "/Auth", "/error", "/makeAppointment/**", "/getDateUpdate/**",
				"/AppointmentBooking/**", };

		http.antMatcher("/**").authorizeRequests().antMatchers(resources).permitAll()
				.antMatchers("/", "/test", "/reacttest**", "/login**", "/Regestration", "/webjars/**",
						"/globalDriveAnalysis**", "/SignatureSetting**", "/createdSignature**",
						"/RevockFileShareWithMe**", "/postRevockAccess***", "/postAccountDelegation**",
						"/createSignatureTemplate**", "/postSelectedUpdateSignature**", "/UpdateSignature**",
						"/UpdateAllUsersSignature/**", "/ERROR**", "/removeCalenderAppointment**",
						"/postSuspendAccounts**", "/postDeleteAccounts**", "/postUnSuspendAccounts**", "/Approve/***",
						"/SetSignature/**", "/DeleteSignature/**", "/SetSignature/**", "/usermanagment/**",
						"/viewProfile/**", "/deleteUser/**", "/DriveStatisticalAnalysis/**", "/getfiledetails/**",
						"/DrivePermissions/**", "/getCurrentUserPermission/**", "/createUser/**", "/userRegistrations",
						"/createUser/**", "/processDeligationForm/**", "/DriveAnalysis/**", "/getData",
						"/calenderppointment/**", "Marketing", "/createMarketingTemplate",
						"/updateCalenderSignature/**", "/calendersettings/**", "/Gsuit-Africa-1/****",
						"/MarketingSetting", "/processUpdateForm/**", "/starts", "/suggestion", "/GmailAnalysis/**",
						"/DashBoard/**", "/Delegation/**", "/DelegateAccount/**", "/createdSignature/**",
						"/RemoveDelegateAccount/**", "/DeleteMarketing/**", "/createdMarketing**",
						"/UpdateAllUsersMarketing/**", "/postSelectedUpdateMarketing**", "/AllsharedFilesByOrg**",
						"/FileOrgPermission/***", "/AdminControl***", "/RoleSetting/**", "/AddRoleAccess**",
						"/loginpage**", "/makerChekerState**", "/RevockRoleAccess/**", "/DomainSetting/**",
						"/BUSINESSCOMAFRICA", "/UPDATECLIENTS", "/Registration/**", "/postRegestartionForm",
						"/UpdateCustomer/**", "/CheckDomainLicense", "/contactReseller", "/UserAppAccess",
						"/RevockUserAppAccess", "/processDeligationAccount", "/SuspendGMSAccount",
						"/UnsuspendGMSAccount", "/ApprovalDashBoard", "/MailMetrix","/GetMetrixData",
//						GULF AFRICAN BANK URL ACCESS
						"/DLPReport","/EmailReport","/getDriveDlpReporting","/CreateEmailDlpReport","/getReportUsingKeyWord",
						"/getReportUsing/**","/File**","/DownloadReport","/DataMigration/**","/BackupEmailData", "/RestoreEmailData",
						"/Settings","/PostUpdateSettingsOptions","/postCreateAlias","/SignatureGallary","/MarketingGallary","/ApproveAction/***",
						"/MetrixAccount/**","/GetMetrixData","/postChangePermisionOwnerShip","/GetDLPDriveReport**"
						)
						
				.permitAll().anyRequest().authenticated().and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll().and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://pixinvent.com/");
			}
		};

	}
}