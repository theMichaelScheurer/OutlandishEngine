package componentArchitecture;

import java.util.HashMap;

/**
 * Generates and holds Enitys. Components of entitys are added and accessed
 * through the EntityManager.
 * 
 * @author Michael
 *
 */
public class EntityManager {
	private HashMap<Class<? extends Component>, HashMap<Entity, ? extends Component>> componentStores;
	private HashMap<Integer, Entity> entitylist;
	private int nextID = 0;

	public EntityManager() {
		componentStores = new HashMap<>();
		entitylist = new HashMap<>();
	}

	/**
	 * Gets all the components and their entitys of a certain type
	 * 
	 * @param componentClass
	 *            Class of the wanted component type
	 * @return HashMap of the components, mapped by their entitys
	 */
	public <T extends Component> HashMap<Entity, T> getEntities(
			Class<T> componentClass) {
		return (HashMap<Entity, T>) componentStores.get(componentClass);
	}

	/**
	 * Method to get a certain component of a certain entity.
	 * 
	 * @param e
	 *            Entity from which the component should come
	 * @param componentClass
	 *            Class of the wanted component type
	 * @return The component of the entity of the wanted type, if existing, else
	 *         null
	 */
	public <T extends Component> T getComponent(Entity e,
			Class<T> componentClass) {

		HashMap<Entity, ? extends Component> store = componentStores
				.get(componentClass);

		if (store == null)
			return null;

		T result = (T) store.get(e);
		if (result == null)
			return null;

		return result;
	}

	/**
	 * Adds a component to an entity
	 * 
	 * @param e
	 *            Entity to which the component should be added
	 * @param component
	 *            Component that should be added
	 */
	public <T extends Component> void addComponent(Entity e, T component) {
		HashMap<Entity, T> store = (HashMap<Entity, T>) componentStores
				.get(component.getClass());
		if (store == null) {
			store = new HashMap<>();
			componentStores.put(component.getClass(), store);
		}
		store.put(e, component);

	}

	protected int getNextID() {
		return nextID;
	}

	/**
	 * Creates a new entity, gives it an id and adds it to the manager
	 * 
	 * @return The new entity
	 */
	public Entity generateEntity() {
		Entity e = new Entity(nextID);
		nextID++;
		registerEntity(e);
		return e;
	}

	/**
	 * Adss a existing entity to the manager
	 * 
	 * @param e
	 *            Entity to be added
	 */
	public void registerEntity(Entity e) {
		entitylist.put(e.id, e);
		e.entityManager = this;
	}

	/**
	 * Returns a entity by its id.
	 * 
	 * @param id
	 *            ID of the wanted entity.
	 * @return The wanted entity.
	 */
	public Entity getEntity(int id) {
		return entitylist.get(id);
	}

	/**
	 * Removes an entity and all its components from the manager.
	 * 
	 * @param e
	 *            Entity to be removed
	 */
	public void killEntity(Entity e) {
		for (HashMap<Entity, ? extends Component> store : componentStores
				.values()) {
			store.remove(e);
		}
	}

}
