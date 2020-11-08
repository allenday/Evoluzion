package com.evoluzion;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.InputProcessor;

public class Keyboard implements InputProcessor {

	World m;
	Screen p;

	public Keyboard(Screen p) {
		this.p = p;

	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {

			case Keys.P:
				p.world.pausaGame = p.world.pausaGame * (-1);

				break;
			case Keys.C:

				p.world.colectorEspesies();
				p.world.guardarDatos();
				p.world.archivarGenotipo();
				p.world.archivarAlelos();
				p.world.archivarFenotipo2();
				break;
			case Keys.ESCAPE:
				p.world.pausaGame = p.world.pausaGame * (-1);

				Object[] options = {p.tx.si, p.tx.no};
				int n = JOptionPane.showOptionDialog(null,
						p.tx.terminarGuardarMensaje, "",

						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
						null, options, options[1]);

				if (n == JOptionPane.YES_OPTION) {
					if (p.todoGuardado == false) {
						if (p.world.organisms.size > 0) {
							p.world.guardarDatos();
							// m.archivarGenoma();
							p.world.archivarGenotipo();
							p.world.archivarFenotipo2();
							p.world.archivarAlelos();
						}
						// m.f_genes.cerrarArchivo();
						// m.f_proteoma.cerrarArchivo();
						// m.f_mutantes.cerrarArchivo();
						p.world.f_datos.cerrarArchivo();
						p.world.f_alelos.cerrarArchivo();
						p.world.f_genotipos.cerrarArchivo();
					}
					p.world.f_fenotipos.cerrarArchivo();

					p.ev.setScreen(new StartMenu(p.ev));
					// m = null;
					p.dispose();
				}
				if (n == JOptionPane.NO_OPTION) {
				}

				break;

		default:
			break;
		}
		return true;

	}

	@Override
	public boolean keyUp(int keycode) {

		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		// screenX=(int) mundo.getbStop().getPosicion().x;

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float v, float v1) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

}
