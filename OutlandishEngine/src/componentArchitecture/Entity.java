package componentArchitecture;
/**
 * An Entity in the Component-Entity-System.
 * Components are added using an EntityManager
 * 
 * @author Michael
 *
 */
public class Entity
{
	public int id;
	public EntityManager entityManager;
	
	public Entity()  {}
	public Entity(int id)
	{
		this.id = id;
	}
	
	/**
	 * Method to get a certain component of an entity.
	 * 
	 * @param type Class of the wanted component type
	 * @return The component of the entity of the wanted type, if existing, else null
	 */
	public <T extends Component> T getComponent(Class<T> type)
	{
		return entityManager.getComponent(this, type);
	}
	
}
