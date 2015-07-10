package com.chaski;

/*
Project Kipu 
Copyright (C) 2013, 2014, 2015  Michael Dorin

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


public abstract class KipuThread implements Comparable<KipuThread> {
	public long time;
	public long reload=1;
	Object param;

	public KipuThread() {}

	public void setReload(long _reload) {
		reload = _reload;
	}
	
	public void setTime(long _time) {
		time = _time;
	}
	
	public abstract void execute();
	public abstract int send(Object param);
	public abstract void setup(Object param);
	
	public int compareTo(KipuThread other) {
		if (other.time < time)
			return 1;
		else if (other.time > time)
			return -1;
		else
			return 0;
	}
}

