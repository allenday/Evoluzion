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

public class Senergy {

	World world;

	private final Sprite image;
	double energia;
	float width = 7;
	float height = 7;
	int speed;
	Vector2 position;
	Vector2 direction;
	Rectangle border;
	boolean visible = true;

	public Senergy(Vector2 position, World world) {

		this.world = world;
		// energy=m.Senergy;
		this.position = position;
		direction = new Vector2();

		width = (int) (width / world.zoom);
		height = (int) (height / world.zoom);

		speed = 30;
		border = new Rectangle();

		border.height = height;
		border.width = width;

		border.x = position.x;
		border.y = position.y;

		direction.y = -1;
		direction.x = 0;

		image = new Sprite(world.organismTexture.getRegions().get(24));
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
		// image.setSize(width, height);
		border.x = position.x;
		border.y = position.y;
		// border.height= height;
		// border.width= width;

		if (position.y < 0) {
			reset();
		}

	}

	public void reset() {

		// position.x= (float) (Math.random()*m.width);
		position.y = world.height + 10;
		if (position.x > world.width / 2) {
			energia = world.SenergiaR;
		}
		if (position.x < world.width / 2) {
			energia = world.Senergia;
		}

		visible = true;

	}

}