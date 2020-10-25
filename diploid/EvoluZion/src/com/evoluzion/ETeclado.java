package com.evoluzion;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.InputProcessor;

public class ETeclado implements InputProcessor {

	Mundo m;
	Pantalla p;

	public ETeclado(Pantalla p) {
		this.p = p;

	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {

		case Keys.P:
			p.m.pausaGame = p.m.pausaGame * (-1);

			break;
		case Keys.C:

			p.m.colectorEspesies();
			p.m.guardarDatos();
			p.m.archivarGenotipo();
			p.m.archivarAlelos();
			p.m.archivarFenotipo2();
			break;
		case Keys.ESCAPE:
			p.m.pausaGame = p.m.pausaGame * (-1);

			Object[] options = { p.tx.si, p.tx.no };
			int n = JOptionPane.showOptionDialog(null,
					p.tx.terminarGuardarMensaje, "",

					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
					null, options, options[1]);

			if (n == JOptionPane.YES_OPTION) {
				if (p.todoGuardado == false) {
					if (p.m.aorg.size > 0) {
						p.m.guardarDatos();
						// m.archivarGenoma();
						p.m.archivarGenotipo();
						p.m.archivarFenotipo2();
						p.m.archivarAlelos();
					}
					// m.f_genes.cerrarArchivo();
					// m.f_proteoma.cerrarArchivo();
					// m.f_mutantes.cerrarArchivo();
					p.m.f_datos.cerrarArchivo();
					p.m.f_alelos.cerrarArchivo();
					p.m.f_genotipos.cerrarArchivo();
				}
				p.m.f_fenotipos.cerrarArchivo();

				p.ev.setScreen(new MenuInicio(p.ev));
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
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

}
