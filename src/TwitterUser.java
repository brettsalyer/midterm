import java.util.ArrayList;
import java.util.HashMap;

public class TwitterUser implements Comparable<TwitterUser>, Cloneable {

	
	private int userId;
	private HashMap<Integer, TwitterUser> usersFollowing;
	private HashMap<Integer, TwitterUser> users = new HashMap<>();
	
	//Default Constructor
	public TwitterUser() {
		
	}
	
	public TwitterUser(int id) {
		this.userId = id;
		usersFollowing = new HashMap<>();
	}

	
	public int getUserId() {
		return userId;
	}

	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	public HashMap<Integer, TwitterUser> getFollowing() {
		return this.usersFollowing;
	}

	
	public void setFollowing(HashMap<Integer, TwitterUser> following) {
		this.usersFollowing = following;
	}
	
	public void addFollowing(TwitterUser friend) {
		this.usersFollowing.put(friend.getUserId(), friend);
	}
	
	public void addUser(TwitterUser user) {
		this.users.put(user.getUserId(), user);
	}
	
	public HashMap<Integer, TwitterUser> getUsers(){
		return this.users;
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
		ArrayList<TwitterUser> following = new ArrayList<TwitterUser>(id.usersFollowing.values());
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
