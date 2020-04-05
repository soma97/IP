package services;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AuthPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 11L;

	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		ExternalContext context = facesContext.getExternalContext();

		boolean loggedIn = LogingManagement.getInstance().checkIfLoggedIn(((HttpSession)context.getSession(true)).getId());

		boolean onLoginPage = (-1 != facesContext.getViewRoot().getViewId().lastIndexOf("login")) ? true : false;
		if (!onLoginPage && !loggedIn) {
			event.getFacesContext().getApplication().getNavigationHandler().handleNavigation(event.getFacesContext(), null, "login");
		}
	}

	public void beforePhase(PhaseEvent event) {

	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
