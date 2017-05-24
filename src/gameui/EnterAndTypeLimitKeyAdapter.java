package gameui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * Provide pressing enter to login and limit number of character when typing
 * on the text field
 * 
 * @author vittunyutamaeprasart
 *
 */
public class EnterAndTypeLimitKeyAdapter extends KeyAdapter{
		private JTextField textfield;
		private Object classUsed;

		/**
		 * initialize adapter without limit typing
		 */
		public EnterAndTypeLimitKeyAdapter(Object givenClass) {
			this(new JTextField(), givenClass);
		}

		/**
		 * initialize adapter with both enter pressing and limit typing
		 * 
		 * @param textField
		 */
		public EnterAndTypeLimitKeyAdapter(JTextField textField, Object givenClass) {
			textfield = textField;
			classUsed = givenClass;
			
		}

		/**
		 * enable limit typing at least 20 characters
		 */
		@Override
		public void keyTyped(KeyEvent e) {
			if (textfield.getText().length() >= 20) {
				e.consume();
			}
		}

		/**
		 * enable enter pressing do action depend on type.
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				if(classUsed instanceof LoginUI){
					LoginUI login = (LoginUI) classUsed;
					login.loginAction();
				}
				if(classUsed instanceof SignUpUI){
					SignUpUI signUp = (SignUpUI) classUsed;
					signUp.confirmAction();
				}
				if(classUsed instanceof CantConnectUI){
					CantConnectUI cantConnect = (CantConnectUI) classUsed;
					cantConnect.closeCantConnectPane();
				}
			}
		}
	
}
