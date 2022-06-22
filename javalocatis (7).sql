-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 22 juin 2022 à 09:43
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `javalocatis`
--

-- --------------------------------------------------------

--
-- Structure de la table `campagne`
--

DROP TABLE IF EXISTS `campagne`;
CREATE TABLE IF NOT EXISTS `campagne` (
  `ID_campagne` int(64) NOT NULL AUTO_INCREMENT,
  `Titre_campagne` varchar(50) NOT NULL,
  `Date_Debut` date NOT NULL,
  `Date_Fin` date NOT NULL,
  `Heure` time NOT NULL,
  `frequence` varchar(20) NOT NULL,
  `DateProchainMail` date DEFAULT NULL,
  `End` int(1) NOT NULL,
  `ID_utilisateur` int(20) NOT NULL,
  `Objet` varchar(100) NOT NULL,
  `Contenu` varchar(1000) NOT NULL,
  PRIMARY KEY (`ID_campagne`),
  UNIQUE KEY `ID_Campagne_IND` (`ID_campagne`),
  KEY `REF_Campa_Utili_IND` (`ID_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `campagne`
--

INSERT INTO `campagne` (`ID_campagne`, `Titre_campagne`, `Date_Debut`, `Date_Fin`, `Heure`, `frequence`, `DateProchainMail`, `End`, `ID_utilisateur`, `Objet`, `Contenu`) VALUES
(1, 'Fête du 30 juin', '2022-06-22', '2022-06-30', '10:00:00', 'Quotidien', NULL, 0, 0, 'Fête du 30 juin', 'une fête est organisé à Dijon 8 rue de la chapelle\nCordialement'),
(2, 'Changement horaire Utilisateurs', '2022-06-22', '2022-07-02', '12:00:00', 'Quotidien', NULL, 0, 0, 'Changement horaire Utilisateurs', 'Changement dans les horaires des utilisateurs.\nConsulter le tableau des horaires.\nCordialement.');

-- --------------------------------------------------------

--
-- Structure de la table `habiter`
--

DROP TABLE IF EXISTS `habiter`;
CREATE TABLE IF NOT EXISTS `habiter` (
  `ID_locataire` int(40) NOT NULL,
  `ID_batiment` int(10) NOT NULL,
  PRIMARY KEY (`ID_locataire`,`ID_batiment`),
  UNIQUE KEY `ID_HABITER_IND` (`ID_locataire`,`ID_batiment`),
  KEY `REF_HABIT_Logem_IND` (`ID_batiment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `habiter`
--

INSERT INTO `habiter` (`ID_locataire`, `ID_batiment`) VALUES
(1, 1),
(3, 1),
(4, 1),
(2, 2),
(5, 3);

-- --------------------------------------------------------

--
-- Structure de la table `listediffusion`
--

DROP TABLE IF EXISTS `listediffusion`;
CREATE TABLE IF NOT EXISTS `listediffusion` (
  `ID_listeDiff` int(64) NOT NULL AUTO_INCREMENT,
  `Nom_liste` varchar(20) NOT NULL,
  PRIMARY KEY (`ID_listeDiff`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `listediffusion`
--

INSERT INTO `listediffusion` (`ID_listeDiff`, `Nom_liste`) VALUES
(1, 'Feminine'),
(2, 'Masculine'),
(3, '30 ans et plus'),
(4, '30 ans et moins'),
(5, 'Utilisateur 1 et 2'),
(6, 'Gestionnaire 1, 2, 3'),
(7, 'Administrateur');

-- --------------------------------------------------------

--
-- Structure de la table `locataire`
--

DROP TABLE IF EXISTS `locataire`;
CREATE TABLE IF NOT EXISTS `locataire` (
  `ID_locataire` int(40) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(20) NOT NULL,
  `Prenom` varchar(20) NOT NULL,
  `Age` int(3) NOT NULL,
  `DateDeNaissance` date NOT NULL,
  `Mail` varchar(100) NOT NULL,
  `Telephone` varchar(10) NOT NULL,
  PRIMARY KEY (`ID_locataire`),
  UNIQUE KEY `ID_Locataire_IND` (`ID_locataire`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `locataire`
--

INSERT INTO `locataire` (`ID_locataire`, `Nom`, `Prenom`, `Age`, `DateDeNaissance`, `Mail`, `Telephone`) VALUES
(1, 'Dupond', 'Yvan', 28, '1994-01-13', 'yvandupont@gmail.com', '0685984571'),
(2, 'De La Marre', 'Marie', 43, '1979-01-05', 'mariedelamarre@gmail.com', '0745985212'),
(3, 'Grand', 'Michel', 22, '2000-06-04', 'michellegrand@gmail.com', '0698589698'),
(4, 'Barn', 'Francesca', 19, '2002-12-05', 'francescabarn@gmail.com', '0741452589'),
(5, 'Potter', 'Harry', 72, '1950-06-17', 'harrypotter@gmail.com', '0685985785');

-- --------------------------------------------------------

--
-- Structure de la table `locataire_liste`
--

DROP TABLE IF EXISTS `locataire_liste`;
CREATE TABLE IF NOT EXISTS `locataire_liste` (
  `ID_listeDiff` int(64) NOT NULL,
  `ID_locataire` int(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `locataire_liste`
--

INSERT INTO `locataire_liste` (`ID_listeDiff`, `ID_locataire`) VALUES
(1, 2),
(1, 4),
(2, 1),
(2, 3),
(2, 5),
(3, 2),
(3, 5),
(4, 1),
(4, 3),
(4, 4);

-- --------------------------------------------------------

--
-- Structure de la table `logement`
--

DROP TABLE IF EXISTS `logement`;
CREATE TABLE IF NOT EXISTS `logement` (
  `ID_batiment` int(10) NOT NULL AUTO_INCREMENT,
  `NumeroRue` varchar(30) NOT NULL,
  `NomRue` varchar(30) NOT NULL,
  `Ville` varchar(30) NOT NULL,
  `CodePostal` varchar(30) NOT NULL,
  `NumeroAppartement` int(10) DEFAULT NULL,
  `NombreEtage` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID_batiment`),
  UNIQUE KEY `ID_Logement_IND` (`ID_batiment`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `logement`
--

INSERT INTO `logement` (`ID_batiment`, `NumeroRue`, `NomRue`, `Ville`, `CodePostal`, `NumeroAppartement`, `NombreEtage`) VALUES
(1, '15', 'Place de la republique', 'Dijon', '21000', 2, 1),
(2, '98', 'Rue des lezars', 'Dijon', '21000', 1, 3),
(3, '21B', 'Rue de la pouille', 'Dijon', '21000', NULL, NULL),
(4, '24', 'Rue des ancres', 'Dijon', '21000', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `recevoir`
--

DROP TABLE IF EXISTS `recevoir`;
CREATE TABLE IF NOT EXISTS `recevoir` (
  `ID_campagne` int(64) NOT NULL,
  `ID_listeDiff` int(40) NOT NULL,
  PRIMARY KEY (`ID_campagne`,`ID_listeDiff`),
  UNIQUE KEY `ID_RECEVOIR_IND` (`ID_campagne`,`ID_listeDiff`),
  KEY `REF_RECEV_Locat_IND` (`ID_listeDiff`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `recevoir`
--

INSERT INTO `recevoir` (`ID_campagne`, `ID_listeDiff`) VALUES
(1, 4),
(2, 5),
(2, 6);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `ID_utilisateur` int(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `Mdp` varchar(50) NOT NULL,
  `CAT` char(5) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_utilisateur`),
  UNIQUE KEY `ID_Utilisateur_IND` (`ID_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`ID_utilisateur`, `login`, `Mdp`, `CAT`, `Email`, `Password`) VALUES
(0, 'Benjamin', 'ben', 'adm', 'theluffydu30@gmail.com', 'bvogzfkcpurcwnnc'),
(1, 'UtilisateurDemo1', 'uti1', 'uti1', 'utilisateurdemo1@gmail.com', 'uti1'),
(2, 'UtilisateurDemo2', 'uti2', 'uti2', 'utilisateurdemo2@gmail.com', 'uti2'),
(3, 'GestionnaireDemo1', 'ges1', 'ges1', 'gestionnairedemo1@gmail.com', 'ges1'),
(4, 'GestionnaireDemo2', 'ges2', 'ges2', 'gestionnairedemo2@gmail.com', 'ges2'),
(5, 'GestionnaireDemo3', 'ges3', 'ges3', 'gestionnairedemo3@gmail.com', 'ges3'),
(6, 'AdministrateurDemo', 'adm', 'adm', 'administrateurdemo@gmail.com', 'adm');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur_liste`
--

DROP TABLE IF EXISTS `utilisateur_liste`;
CREATE TABLE IF NOT EXISTS `utilisateur_liste` (
  `ID_listeDiff` int(20) NOT NULL,
  `ID_utilisateur` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `utilisateur_liste`
--

INSERT INTO `utilisateur_liste` (`ID_listeDiff`, `ID_utilisateur`) VALUES
(5, 2),
(5, 1),
(6, 5),
(6, 4),
(6, 3),
(7, 0),
(7, 6);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
