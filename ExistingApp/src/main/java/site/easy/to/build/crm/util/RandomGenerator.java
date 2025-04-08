package site.easy.to.build.crm.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.Timestamp;

public class RandomGenerator {

    public static Timestamp genRandomDate(int anneeMin, int anneeMax) {
        // Définir la date de début (1er janvier de l'année min à 00:00:00)
        LocalDateTime startDate = LocalDateTime.of(anneeMin, 1, 1, 0, 0, 0, 0);

        // Définir la date de fin (31 décembre de l'année max à 00:00:00)
        LocalDateTime endDate = LocalDateTime.of(anneeMax, 12, 31, 0, 0, 0, 0);

        // Convertir les dates en millisecondes depuis l'époque Unix
        long startMillis = startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endMillis = endDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // Générer un instant aléatoire en millisecondes entre les bornes, inclusivement
        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis + 1);

        // Convertir le nombre de millisecondes aléatoires en LocalDateTime
        LocalDateTime randomDate = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(randomMillis),
                ZoneId.systemDefault());

        // Ajuster l'heure pour être à 00:00:00.000
        randomDate = randomDate.withHour(0).withMinute(0).withSecond(0).withNano(0);

        // Convertir le LocalDateTime ajusté en Timestamp
        return Timestamp.valueOf(randomDate);
    }

    public static int genRandomInt(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public static double genRandomDouble(double min, double max) {
        Random random = new Random();
        double randomValue = random.nextDouble(min, max + Double.MIN_VALUE);
        return Math.floor(randomValue * 1000) / 1000;
    }


}
