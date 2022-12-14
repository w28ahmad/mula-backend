
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
class AWSConfig() {

    @Bean
    fun s3Client(): AmazonS3 {
        val credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance()

        return AmazonS3ClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.US_EAST_1)
            .build()
    }
}