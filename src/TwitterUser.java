import java.util.ArrayList;

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
		return this.userId;
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
	

}
