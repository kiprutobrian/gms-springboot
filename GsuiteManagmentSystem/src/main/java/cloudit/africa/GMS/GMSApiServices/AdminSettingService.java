package cloudit.africa.GMS.GMSApiServices;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.UserRole;
import cloudit.africa.GMS.Model.Checkers;
import cloudit.africa.GMS.Model.RoleAccess;

public interface AdminSettingService {

	boolean addRoleAccess(RoleAccess roleAccess);

	boolean AddMarkerChecker(Checkers checkers);

	boolean updateActiveState(Checkers checkers);

	boolean revockRoleAccess(UserRole userRole, String roleId);

	Checker getChecker(String i);

}
