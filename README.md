# hifive

## Introducing 5QL - the HiFive Query Language ##
5QL is *almost* SQL.

Use the name of java properties inside classic SQL. No limitation.

````sql
SELECT spent / budget FROM Project WHERE id = :id
````

````java
public class Project {
	private String id;
	private Double spent;
	private Double budget;
}
````