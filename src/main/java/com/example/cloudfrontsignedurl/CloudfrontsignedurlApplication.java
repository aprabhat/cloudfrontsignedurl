package com.example.cloudfrontsignedurl;

import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;

@SpringBootApplication
public class CloudfrontsignedurlApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CloudfrontsignedurlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String cloudFrontKeyPairId = "<public_key_id>"; // public key id created in cloud front key management section.
		String distributionDomain = "<cloud_front_distribution_name>"; // cloud front distribution domain name.
		String key = "cloudfrontsignedurl/objetc1.txt"; // S3 bucket object path

		Date expirationDate = new Date(System.currentTimeMillis() + 7200000); // Token will be valid for 2 hour
		try {
			File cloudFrontPrivateKeyFile = generateCloudFrontPrivateKeyFile();
			String signedUrl = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(SignerUtils.Protocol.https,
					distributionDomain, cloudFrontPrivateKeyFile, key, cloudFrontKeyPairId, expirationDate);
			System.out.println(signedUrl);

		} catch (IOException | InvalidKeySpecException exception) {
			throw new Exception(exception.getMessage());
		}
	}

	public File generateCloudFrontPrivateKeyFile() {
		File file = new File("<private_key_file>"); // Path to public/private key pair file.
		return file;
	}

}
