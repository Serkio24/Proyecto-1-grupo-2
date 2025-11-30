package uniandes.edu.co.proyecto.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.bson.Document;

@Repository
public class Top20ConductoresRepository {
    
    private final MongoTemplate mongoTemplate;

    public Top20ConductoresRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Document> obtenerTop20Conductores(){
        List<Document> pipeline = List.of(

            new Document("$group", new Document("_id", "$idConductor")
                .append("numeroViajes", new Document("$sum", 1))),
            
                new Document("$sort", new Document("numeroViajes", -1)),
  
            new Document("$limit", 20),

            new Document("$lookup", new Document("from", "USUARIOS")
                .append("localField", "_id")
                .append("foreignField", "_id")
                .append("as", "conductor")),

            new Document("$unwind", new Document("path", "$conductor")),

            new Document("$project", new Document("idConductor", "$_id")
                .append("nombreConductor", "$conductor.nombre")
                .append("numeroViajes", 1)
                .append("_id", 0))
        );
        return mongoTemplate.getCollection("VIAJES")
            .aggregate(pipeline)
            .into(new java.util.ArrayList<>());
    }
}
