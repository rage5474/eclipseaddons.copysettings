package de.raphaelgeissler.eclipseaddons.copysettings.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.raphaelgeissler.eclipseaddons.copysettings.service.CopySettingsService;

public class ApplySettingsHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(selection instanceof IStructuredSelection)
		{
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if(structuredSelection.size() >= 1)
			{
				List<IJavaProject> pathsToJavaProjects = new ArrayList<>();
				List<String> pathTosEttingsFolders = new ArrayList<>();
				
				for(Object nextElement : structuredSelection.toList())
				{
					if(nextElement instanceof IJavaProject)
					{
						IJavaProject nextJavaProject = (IJavaProject) nextElement;
						pathsToJavaProjects.add(nextJavaProject);
						pathTosEttingsFolders.add(nextJavaProject.getProject().getLocationURI().getPath() + "/.settings");
					}
				}
				
				CopySettingsService.copySettingsFolder(pathTosEttingsFolders);
				
				for(IJavaProject nextJavaProject : pathsToJavaProjects)
					try {
						nextJavaProject.getProject().refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}

			
		}
		
		return null;
	}

}
