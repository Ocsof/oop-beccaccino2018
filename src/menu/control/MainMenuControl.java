package menu.control;

import javafx.event.ActionEvent;

public interface MainMenuControl {
	
	public void startClicked(ActionEvent event);
	
	public void createProfileClicked(ActionEvent event);

	public void settingsClicked(ActionEvent event);

	public void exitClicked();

}
