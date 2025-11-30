package uniandes.edu.co.proyecto.repositories;

import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsoServiciosRepository {

    private final MongoTemplate mongoTemplate;

    public UsoServiciosRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Document> obtenerUsoPorCiudadYFechas(String ciudad, Date desde, Date hasta) {
        List<Document> pipeline = List.of(
            new Document("$match", new Document("puntoOrigen.ciudad", ciudad)
                .append("fechaHora", new Document("$gte", desde).append("$lte", hasta))),
            new Document("$group", new Document("_id", new Document("tipoServicio", "$tipoServicio").append("nivel", "$nivelRequerido"))
                .append("cantidad", new Document("$sum", 1))),
            new Document("$sort", new Document("cantidad", -1)),
            new Document("$group", new Document("_id", null)
                .append("total", new Document("$sum", "$cantidad"))
                .append("items", new Document("$push", new Document("tipoServicio", "$_id.tipoServicio")
                    .append("nivel", "$_id.nivel")
                    .append("cantidad", "$cantidad")))),
            new Document("$unwind", "$items"),
            new Document("$project", new Document("_id", 0)
                .append("tipoServicio", "$items.tipoServicio")
                .append("nivel", "$items.nivel")
                .append("cantidad", "$items.cantidad")
                .append("porcentaje", new Document("$round", List.of(new Document("$multiply", List.of(new Document("$divide", List.of("$items.cantidad", "$total")), 100)), 2)))),
            new Document("$sort", new Document("cantidad", -1))
        );
        return mongoTemplate.getCollection("VIAJES")
            .aggregate(pipeline)
            .into(new java.util.ArrayList<>());
    }
}
