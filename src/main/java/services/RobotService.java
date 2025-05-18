package services;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import data.Robot;

@Path("/robotservice")
public class RobotService {
    

    @GET
    @Path("/info")
    @Produces(MediaType.TEXT_PLAIN)
    public String info() {
        return "This is working...";
    }

    // Reading all robots from the table
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Robot> readAllRobot() {
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("jager");
        EntityManager em = emf.createEntityManager();
        List<Robot> list = em.createQuery("select a from Robot a", Robot.class).getResultList();
        return list;
    }

    // Adding one robot object into the table
    @POST
    @Path("/addrobot")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Robot postRobot(Robot robot) {
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("jager");
        EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(robot);
            em.getTransaction().commit();
            return robot;
    }

    // Fetch the latest robot based on highest ID (assuming ID is auto-incremented)
    @GET
    @Path("/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public Robot getLatestRobotSettings() {
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("jager");
        EntityManager em = emf.createEntityManager();
        Robot latestRobot = em.createQuery("SELECT a FROM Robot a ORDER BY a.id DESC", Robot.class)
                              .setMaxResults(1)
                              .getSingleResult();
        return latestRobot;
    }
}