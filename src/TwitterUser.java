import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

public class TwitterUser implements Comparable<TwitterUser>, Cloneable {

	
	private int userId;
	private ArrayList<TwitterUser> usersFollowing;
	
	//Default Constructor
	public TwitterUser() {
		
	}
	
	public TwitterUser(int id) {
		this.userId = id;
		usersFollowing = new ArrayList<>();
	}

	
	public int getUserId() {
		return userId;
	}

	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	public ArrayList<TwitterUser> getFollowing() {
		return this.usersFollowing;
	}

	
	public void setFollowing(ArrayList<TwitterUser> following) {
		this.usersFollowing = following;
	}
	
	public void addFollowing(TwitterUser user) {
		this.usersFollowing.add(user);
	}
	
	
	@Override
	public int compareTo(TwitterUser o) {
		if(o.userId == this.userId)
			return 0;
		if(o.userId < this.userId)
			return -1;
		else
			return 1;
	}
	
	@Override
	protected TwitterUser clone() throws CloneNotSupportedException {
		TwitterUser user = null;

		if(this.usersFollowing!=null){
			user = new TwitterUser(this.userId);
			
		}
		return user;
	}
	
	public ArrayList<TwitterUser> getNeighborhood(TwitterUser id,int depth){
		ArrayList<TwitterUser> following = new ArrayList<TwitterUser>(id.getFollowing());
		ArrayList<TwitterUser> neighbordhood = new ArrayList<>();
		
		if(depth > 0 && following!=null && following.size()>0){
			for(TwitterUser user : following){
				neighbordhood.add(user);
				ArrayList<TwitterUser> follower = getNeighborhood(user, depth-1);
				for(TwitterUser f1 : follower){
					if(!neighbordhood.contains(f1)){
						neighbordhood.add(f1);
					}
				}
			}
			
		}
		return neighbordhood;
	}

}
