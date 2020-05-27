package cloudit.africa.GMS.Entity;

import java.util.List;

public class workflowActionAccounts {

	private Integer Id;

	private WorkFlow workflow;
	
	private List<OrganizationMembers> accountsAction;
	
	
	public workflowActionAccounts(){
		
	}
	
	public workflowActionAccounts(WorkFlow workflow, List<OrganizationMembers> accountsAction) {
		super();
		this.workflow = workflow;
		this.accountsAction = accountsAction;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public WorkFlow getWorkflow() {
		return workflow;
	}
	public void setWorkflow(WorkFlow workflow) {
		this.workflow = workflow;
	}
	public List<OrganizationMembers> getAccountsAction() {
		return accountsAction;
	}
	public void setAccountsAction(List<OrganizationMembers> accountsAction) {
		this.accountsAction = accountsAction;
	}

	
	
}
