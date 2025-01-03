-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           8.0.30 - MySQL Community Server - GPL
-- SE du serveur:                Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour camp_activites
CREATE DATABASE IF NOT EXISTS `camp_activites` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `camp_activites`;

-- Listage de la structure de table camp_activites. activites
CREATE TABLE IF NOT EXISTS `activites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `age_min` int NOT NULL,
  `age_max` int NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table camp_activites.activites : ~4 rows (environ)
INSERT INTO `activites` (`id`, `nom`, `age_min`, `age_max`, `description`) VALUES
	(1, 'Course en forêt', 8, 14, 'Activité de course pour les jeunes aventuriers.'),
	(2, 'Atelier de peinture', 5, 12, 'Un moment de créativité pour les enfants.'),
	(3, 'Escalade', 10, 18, 'Initiation à l\'escalade pour les amateurs de sensations fortes.'),
	(4, 'Randonnée', 12, 20, 'Parcours de randonnée dans les montagnes.');

-- Listage de la structure de table camp_activites. calendrier
CREATE TABLE IF NOT EXISTS `calendrier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `activite_id` int NOT NULL,
  `debut` datetime NOT NULL,
  `fin` datetime NOT NULL,
  `lieu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `activite_id` (`activite_id`),
  CONSTRAINT `calendrier_ibfk_1` FOREIGN KEY (`activite_id`) REFERENCES `activites` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table camp_activites.calendrier : ~4 rows (environ)
INSERT INTO `calendrier` (`id`, `activite_id`, `debut`, `fin`, `lieu`) VALUES
	(1, 1, '2025-01-10 09:00:00', '2025-01-10 11:00:00', 'Forêt des Aventures'),
	(2, 2, '2025-01-11 14:00:00', '2025-01-11 16:00:00', 'Salle des Arts'),
	(3, 3, '2025-01-12 10:00:00', '2025-01-12 12:30:00', 'Mur d\'escalade extérieur'),
	(4, 4, '2025-01-13 08:00:00', '2025-01-13 12:00:00', 'Parc National');

-- Listage de la structure de table camp_activites. notifications
CREATE TABLE IF NOT EXISTS `notifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `participant_id` int NOT NULL,
  `message` text NOT NULL,
  `etat_envoye` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `participant_id` (`participant_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`participant_id`) REFERENCES `participants` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table camp_activites.notifications : ~3 rows (environ)
INSERT INTO `notifications` (`id`, `participant_id`, `message`, `etat_envoye`) VALUES
	(1, 1, 'Votre activité commence dans 30 minutes !', 1),
	(2, 2, 'N\'oubliez pas l\'atelier de peinture demain.', 0),
	(3, 3, 'Votre session d\'escalade débute bientôt.', 0);

-- Listage de la structure de table camp_activites. participants
CREATE TABLE IF NOT EXISTS `participants` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `age` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table camp_activites.participants : ~0 rows (environ)
INSERT INTO `participants` (`id`, `nom`, `age`) VALUES
	(1, 'Jean Dupont', 12),
	(2, 'Marie Curie', 10),
	(3, 'Paul Durand', 15),
	(4, 'Alice Martin', 8);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
