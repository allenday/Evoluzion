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

public class Qenergy {

	World world;

	private final Sprite image;
	double mass;
	float width = 7;
	float height = 7;
	int speed;
	Vector2 position;
	Vector2 direction;
	Rectangle border;
	boolean visible = true;

	public Qenergy(Vector2 position, boolean move, int mass, World world) {

		this.world = world;
		this.mass = mass;
		this.position = position;
		direction = new Vector2();

		width = (width / world.zoom);
		height = (height / world.zoom);

		speed = 30;
		border = new Rectangle();

		border.height = height;
		border.width = width;

		border.x = position.x;
		border.y = position.y;

		if (move == true) {
			direction.y = -1;
		}
		direction.x = 0;

		image = new Sprite(world.organismTexture.getRegions().get(23));
		image.setPosition(this.position.x, this.position.y);
		image.setSize(width, height);

	}

	public void viewObject(SpriteBatch sb) {
		if (visible == true) {
			sb.begin();
			image.draw(sb);
			sb.end();
		}

	}

	public void dibujar(ShapeRenderer sr) {

		if (visible == true) {
			sr.setColor(Color.RED);

			sr.filledCircle(position.x, position.y, width);

		}
	}

	public void viewBorder(ShapeRenderer sr) {

		sr.begin(ShapeType.Rectangle);

		sr.setColor(Color.CYAN);
		sr.rect(border.x, border.y, border.width, border.height);
		sr.end();

	}

	public void update() {

		position.add(Gdx.graphics.getDeltaTime() * (direction.x) * speed,
				Gdx.graphics.getDeltaTime() * (direction.y) * speed);

		image.setPosition(position.x, position.y);
		border.x = position.x;
		border.y = position.y;

		if (position.y < 0) {
			reset1();
		}
		if (position.y > world.height - 50 && direction.y == 0) {
			reset1();
		}
		if (position.x < 0 || position.x > world.width) {
			reset2();
		}

	}

	public void reset1() {
		if (direction.y != 0) {
			position.y = world.height;
		}
		if (direction.y == 0) {
			position.y = (float) (Math.random() * world.height);
		}
	}

	public void reset2() {

		if (position.x < world.width / 2) {
			position.x = (float) (Math.random() * world.width / 2);
		}
		if (position.x > world.width / 2) {
			position.x = (float) Math.random() * (width - (width / 2)) + width
					/ 2;
		}
	}

	public void reset3() {

		position.x = world.width / 2;
	}

}