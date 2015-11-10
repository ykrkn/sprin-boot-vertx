package sx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sx.verticle.IVerticleFactory;
import sx.verticle.rpc.RequestCodec;
import sx.verticle.rpc.ResponseCodec;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBootVertxApplication {

    @Autowired
    IVerticleFactory verticleFactory;

    @Bean
    public Vertx vertx(){
        return Vertx.vertx();
    }

    @PostConstruct
    private void init() {
        DeploymentOptions options = new DeploymentOptions().setWorker(true);
        vertx().eventBus().registerCodec(new RequestCodec());
        vertx().eventBus().registerCodec(new ResponseCodec());
        vertx().deployVerticle( verticleFactory.createQueryProducer("source_url"), options );
        vertx().deployVerticle( verticleFactory.createExtractor(createConnector()), options );
        vertx().deployVerticle( verticleFactory.createTransformer("source_url"), options );
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootVertxApplication.class, args);
    }

    private IConnector createConnector(){
        return () -> "source_url";
    }
}
