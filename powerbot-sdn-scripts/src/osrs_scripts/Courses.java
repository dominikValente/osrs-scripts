package osrs_scripts;

import java.util.ArrayList;
import org.powerbot.script.Tile;

public class Courses {
	
	Tile startTile;
	ArrayList<Obstacle> list;
	
	public ArrayList<Obstacle> GetCourse(ArrayList<Obstacle> list, String name) {
		
		switch(name) {
		
		case "Draynor":
			startTile = new Tile(3103, 3279, 0);
			
			list.add(new Obstacle("Climb", 11404, 3103, 3276, 3106, 3281, 0));
			list.add(new Obstacle("Cross", 11405, 3102, 3276, 3096, 3281, 3));
			list.add(new Obstacle("Cross", 11406, 3092, 3277, 3087, 3271, 3));
			list.add(new Obstacle("Balance", 11430, 3088, 3264, 3095, 3267, 3));
			list.add(new Obstacle("Jump-up", 11630, 3088, 3262, 3088, 3256, 3));
			list.add(new Obstacle("Jump", 11631, 3087, 3255, 3095, 3255, 3));
			list.add(new Obstacle("Climb-down", 11632, 3095, 3256, 3102, 3261, 3));
			break;
			
		case "AlKharid":
			startTile = new Tile(3273, 3195, 0);
			
			list.add(new Obstacle("Climb", 11633, 3278, 3195, 3270, 3200, 0));				//rough wall
			list.add(new Obstacle("Cross", 14398, 3271, 3193, 3279, 3180, 3));				//tight rope 1
			list.add(new Obstacle("Swing-across", 14402, 3173, 3174, 3266, 3160, 3));		//cable
			list.add(new Obstacle("Teeth-grip", 14405, 3283, 3177, 3303, 3160, 3));			//zip line
			list.add(new Obstacle("Swing-across", 14404, 3312, 3160, 3319, 3165, 1));		//tropical tree
			list.add(new Obstacle("Climb", 11634, 3312, 3179, 3318, 3174, 2));				//roof top beams
			list.add(new Obstacle("Cross", 14409, 3319, 3180, 3312, 3186, 3));				//tight rope 2
			list.add(new Obstacle("Jump", 14399, 3306, 3186, 3291, 3194, 3));
			break;
			
		case "Varrock":
			startTile = new Tile(3221, 3414, 0);
			
			list.add(new Obstacle("Climb", 14412, 3221, 3410, 3227, 3420, 0));				//rough wall
			list.add(new Obstacle("Cross", 14413, 3219, 3420, 3214, 3409, 3)); 				//clothes line
			list.add(new Obstacle("Leap", 14414, 3209, 3412, 3200, 3418, 3));				//gap 1
			list.add(new Obstacle("Balance", 14832, 3195, 3415, 3198, 3417, 1));			//wall
			list.add(new Obstacle("Leap", 14833, 3192, 3407, 3198, 3401, 3));				//gap 2
			list.add(new Obstacle("Leap", 14834, 3181, 3381, 3209, 3404, 3));				//gap 3
			list.add(new Obstacle("Leap", 14835, 3217, 3392, 3233, 3403, 3));				//gap 4
			list.add(new Obstacle("Hurdle", 14836, 3236, 3402, 3241, 3408, 3));				//ledge
			list.add(new Obstacle("Jump-off", 14841, 3241, 3410, 3235, 3415, 3));			//edge 
			break;
			
		case "Canifis":
			startTile = new Tile(3508, 3488, 0);
			
			list.add(new Obstacle("Climb", 14843, 3504, 3490, 3511, 3485, 0)); 				//tall tree
			list.add(new Obstacle("Jump", 14844, 3504, 3498, 3510, 3491, 2));				//gap 1
			list.add(new Obstacle("Jump", 14845, 3504, 3504, 3497, 3507, 2));				//gap 2
			list.add(new Obstacle("Jump", 14848, 3493, 3505, 3487, 3498, 2));				//gap 3
			list.add(new Obstacle("Jump", 14846, 3480, 3500, 3474, 3491, 3));				//gap 4
			list.add(new Obstacle("Vault", 14894, 3485, 3489, 3476, 3480, 2)); 				//pole-vault
			list.add(new Obstacle("Jump", 14847, 3485, 3480, 3505, 3469, 3)); 				//gap 5
			list.add(new Obstacle("Jump", 14897, 3507, 3473, 3516, 3483, 2)); 				//gap 6
			break;
			
		case "Falador":
			startTile = new Tile(3036, 3340, 0);
			
			list.add(new Obstacle("Climb", 14898, 3033, 3341, 3040, 3337, 0));				//rough wall
			list.add(new Obstacle("Cross", 14899, 3035, 3343, 3041, 3342, 3));				//tight rope
			list.add(new Obstacle("Cross", 14901, 3043, 3340, 3052, 3349, 3));				//hand holds
			list.add(new Obstacle("Jump", 14903, 3051, 3357, 3047, 3358, 3));				//gap 1
			list.add(new Obstacle("Jump", 14904, 3049, 3361, 3044, 3367, 3));				//gap 2
			list.add(new Obstacle("Cross", 14905, 3041, 3360, 3034, 3365, 3));				//tight rope 1
			list.add(new Obstacle("Cross", 14911, 3025, 3354, 3030, 3352, 3));				//tight rope 2
			list.add(new Obstacle("Jump", 14919, 3021, 3357, 3009, 3352, 3));				//gap
			list.add(new Obstacle("Jump", 14920, 3016, 3350, 3022, 3342, 3));				//ledge 1
			list.add(new Obstacle("Jump", 14921, 3014, 3347, 3010, 3344, 3));				//ledge 2
			list.add(new Obstacle("Jump", 14922, 3014, 3342, 3008, 3335, 3));				//ledge 3
			list.add(new Obstacle("Jump", 14923, 3014, 3342, 3008, 3335, 3));				//ledge 4
			list.add(new Obstacle("Jump", 14924, 3011, 3333, 3018, 3331, 3));				//ledge 5
			list.add(new Obstacle("Jump", 14925, 3019, 3331, 3025, 3335, 3));				//edge
			break;
		}
		return list;
		
	}

}
