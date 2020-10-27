/*Copyright 2014 Adolfo R. Zurita*/

/*This file is part of EvoluZion.

 EvoluZion is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 EvoluZion is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with EvoluZion.  If not, see <http://www.gnu.org/licenses/>.*/

package com.evoluzion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Qenergia {

	Mundo m;

	private Sprite imagen;
	double masa;
	float ancho = 7;
	float alto = 7;
	int speed;
	Vector2 posicion;
	Vector2 direccion;
	Rectangle borde;
	boolean visible = true;

	public Qenergia(Vector2 posicion, boolean mover, int masa, Mundo m) {

		this.m = m;
		this.masa = masa;
		this.posicion = posicion;
		direccion = new Vector2();

		ancho =  (ancho / m.zoom);
		alto =  (alto / m.zoom);

		speed = 30;
		borde = new Rectangle();

		borde.height = (float) alto;
		borde.width = (float) ancho;

		borde.x = posicion.x;
		borde.y = posicion.y;

		if (mover == true) {
			direccion.y = -1;
		}
		direccion.x = 0;

		imagen = new Sprite(m.textura_organismos.getRegions().get(23));
		imagen.setPosition(this.posicion.x, this.posicion.y);
		imagen.setSize(ancho, alto);

	}

	public void verObjeto(SpriteBatch sb) {
		if (visible == true) {
			sb.begin();
			imagen.draw(sb);
			sb.end();
		}

	}

	public void dibujar(ShapeRenderer sr) {

		if (visible == true) {
			sr.setColor(Color.RED);

			sr.filledCircle(posicion.x, posicion.y, ancho);

		}
	}

	public void verBorde(ShapeRenderer sr) {

		sr.begin(ShapeType.Rectangle);

		sr.setColor(Color.CYAN);
		sr.rect(borde.x, borde.y, borde.width, borde.height);
		sr.end();

	}

	public void update() {

		posicion.add(Gdx.graphics.getDeltaTime() * (direccion.x) * speed,
				Gdx.graphics.getDeltaTime() * (direccion.y) * speed);

		imagen.setPosition(posicion.x, posicion.y);
		borde.x = posicion.x;
		borde.y = posicion.y;

		if (posicion.y < 0) {
			reset1();
		}
		if (posicion.y > m.alto - 50 && direccion.y == 0) {
			reset1();
		}
		if (posicion.x < 0 || posicion.x > m.ancho) {
			reset2();
		}

	}

	public void reset1() {
		if (direccion.y != 0) {
			posicion.y = m.alto;
		}
		if (direccion.y == 0) {
			posicion.y = (float) (Math.random() * m.alto);
		}
	}

	public void reset2() {

		if (posicion.x < m.ancho / 2) {
			posicion.x = (float) (Math.random() * m.ancho / 2);
		}
		if (posicion.x > m.ancho / 2) {
			posicion.x = (float) Math.random() * (ancho - (ancho / 2)) + ancho
					/ 2;
		}
	}

	public void reset3() {

		posicion.x = m.ancho / 2;
	}

}