/*vim: bg=dark et ai sw=4 ts=4 */

/**
 * 
 * Copyright 2017, Philippe DELORME
 * 
 * This file is part of LEDTileTester.
 * 
 * LEDTileTester is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package utils;

import java.awt.Color;
import java.util.Random;


public class Utils
{
    static public Color getColor(final int color)
    {
        // random
        Random randomGenerator = new Random();
        int rand1 = randomGenerator.nextInt(256);
        int rand2 = randomGenerator.nextInt(256);
        int rand3 = randomGenerator.nextInt(256);
        return new Color(rand1, rand2, rand3);
    }
}

