package de.raphaelgeissler.eclipseaddons.copysettings.ui;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import de.raphaelgeissler.eclipseaddons.copysettings.service.CopySettingsService;

public class CopySettingsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		storeSettingsFolder(event);
		return null;
	}

	private void storeSettingsFolder(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() != 1) {
				System.out.println("Select only one project.");
				return;
			}

			Object element = structuredSelection.getFirstElement();
			if (element instanceof IJavaProject) {
				IJavaProject javaProject = (IJavaProject) element;
				File projectFolder = new File(javaProject.getProject().getLocationURI().getPath());

				File settingsFolder = new File(projectFolder, ".settings");
				if (settingsFolder.exists())
					CopySettingsService.storeSettingsFolder(settingsFolder.getAbsolutePath());
				else
					System.out.println("No Settings found...");
			}

			enableApplyMenuItem(event);
		}
	}

	private void enableApplyMenuItem(ExecutionEvent event) {
		ISourceProviderService sourceProviderService = (ISourceProviderService) HandlerUtil
				.getActiveWorkbenchWindow(event).getService(ISourceProviderService.class);
		// now get my service
		CommandState commandStateService = (CommandState) sourceProviderService
				.getSourceProvider(CommandState.MY_STATE);
		commandStateService.toogleEnabled();
	}

}
