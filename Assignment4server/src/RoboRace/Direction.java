package RoboRace;

public enum Direction {
	North {
		public Direction rotate(boolean clockwise) {
			if (clockwise) return East;
			return West;
		}
	},
	East {
		public Direction rotate(boolean clockwise) {
			if (clockwise) return South;
			return North;
		}
	},
	South {
		public Direction rotate(boolean clockwise) {
			if (clockwise) return West;
			return East;
		}
	},
	West {
		public Direction rotate(boolean clockwise) {
			if (clockwise) return North;
			return South;
		}
	};
	
	public abstract Direction rotate(boolean clockwise);
	
	public Direction halfturn() {
		return rotate(true).rotate(true);
	}
}