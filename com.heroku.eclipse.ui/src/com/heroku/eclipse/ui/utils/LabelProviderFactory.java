package com.heroku.eclipse.ui.utils;

import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.heroku.api.App;
import com.heroku.api.Collaborator;
import com.heroku.api.Proc;

public class LabelProviderFactory {
	/*
	 * ==========================================
	 * App Element
	 * ==========================================
	 */
	
	public static ColumnLabelProvider createName(final RunnableWithReturn<List<Proc>, App> procListCallback) {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if( element instanceof App ) {
					App app = (App) element;
					return app.getName();	
				} else if( element instanceof Proc ) {
					Proc proc = (Proc) element;
					return proc.getProcess();
				}
				return "";
			}
			
			@Override
			public Image getImage(Object element) {
				if( element instanceof App ) {
					List<Proc> l = procListCallback.run((App) element);
					if( l != null ) {
						ProcessState total = ProcessState.UNKNOWN;
						for( Proc p : l ) {
							ProcessState s = ProcessState.parseRest(p.getState());
							if( s.ordinal() < total.ordinal() ) {
								total = s;
							}
						}
						return getStateIcon(total);
					}
				} else if( element instanceof Proc ) {
					Proc p = (Proc)element;
					return getStateIcon(ProcessState.parseRest(p.getState()));
				}
				
				// TODO Auto-generated method stub
				return super.getImage(element);
			}
			
			private Image getStateIcon(ProcessState state) {
				if( state == ProcessState.IDLE ) {
					return IconKeys.getImage(IconKeys.ICON_PROCESS_IDLE);
				} else if( state == ProcessState.UP ) {
					return IconKeys.getImage(IconKeys.ICON_PROCESS_UP);
				} else {
					return IconKeys.getImage(IconKeys.ICON_PROCESS_UNKNOWN);
				}
			}
		};
	}
	
	public static ColumnLabelProvider createApp_GitUrl() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if( element instanceof App ) {
					App app = (App) element;
					return app.getGitUrl();	
				}
				return "";
			}
		};
	}
	
	public static ColumnLabelProvider createApp_Url() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if( element instanceof App ) {
					App app = (App) element;
					return app.getWebUrl();	
				}
				return "";
			}
		};
	}
	
	/*
	 * ==========================================
	 * Contributor Element
	 * ==========================================
	 */
	
	public static ColumnLabelProvider createCollaborator_Email() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Collaborator c = (Collaborator) element;
				return c.getEmail();
			}
		};
	}
	
	public static ColumnLabelProvider createCollaborator_Owner(final RunnableWithReturn<Boolean, Collaborator> ownerCheckCallback) {
		return new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
				return "";
			}
			
			@Override
			public Image getImage(Object element) {
				if( ownerCheckCallback.run((Collaborator) element) ) {
					return IconKeys.getImage(IconKeys.ICON_APPLICATION_OWNER); 
				}
				return super.getImage(element);
			}
		};
	}
}
