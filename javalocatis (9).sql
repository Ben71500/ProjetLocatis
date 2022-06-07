-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 28 mai 2022 à 19:35
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
  `Titre_campagne` varchar(20) NOT NULL,
  `Date_Debut` date NOT NULL,
  `Date_Fin` date NOT NULL,
  `Heure` time NOT NULL,
  `frequence` varchar(20) NOT NULL,
  `ID_utilisateur` int(20) NOT NULL,
  `Objet` varchar(20) NOT NULL,
  `Contenu` varchar(64) NOT NULL,
  PRIMARY KEY (`ID_campagne`),
  UNIQUE KEY `ID_Campagne_IND` (`ID_campagne`),
  KEY `REF_Campa_Utili_IND` (`ID_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `campagne`
--

INSERT INTO `campagne` (`ID_campagne`, `Titre_campagne`, `Date_Debut`, `Date_Fin`, `Heure`, `frequence`, `ID_utilisateur`, `Objet`, `Contenu`) VALUES
(1, 'titre', '2022-05-25', '2022-05-25', '00:00:00', 'Une seule fois', 3, '', ''),
(2, 'l2', '2022-05-26', '2022-12-26', '00:00:00', 'Quotidien', 3, 'o2', '222'),
(3, 't3', '2022-05-28', '2024-05-18', '05:00:00', 'Mensuel', 3, 'obj3', 'txt3'),
(4, 't4', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, 'obj4', 'txt4'),
(5, '5', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, '5', '5'),
(6, '66', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, '6', '6'),
(7, 't7', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, 'o7', 'txt7'),
(8, '8', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, '8', '8'),
(9, '9', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, '9', '9'),
(10, '10', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, '10', '10'),
(11, '11', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, '11', '11'),
(12, '12', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, '12', '12'),
(13, '13', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, '13', '13'),
(14, 'titre14', '2022-05-31', '2022-06-07', '02:10:00', 'Quotidien', 3, 'obj14', 'txt14'),
(15, 't15', '2022-05-28', '2022-05-28', '00:00:00', 'Une seule fois', 3, 'o15', 't15'),
(16, 'titre16', '2022-05-28', '2022-06-29', '04:00:00', 'Mensuel', 3, 'obj16', ' '),
(17, 't17', '2022-05-30', '2022-10-26', '23:59:00', 'Mensuel', 3, 'obj17', '17'),
(19, '18', '2022-05-28', '2022-05-28', '03:02:00', 'Une seule fois', 3, '18', '18');

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

-- --------------------------------------------------------

--
-- Structure de la table `listediffusion`
--

DROP TABLE IF EXISTS `listediffusion`;
CREATE TABLE IF NOT EXISTS `listediffusion` (
  `ID_listeDiff` int(64) NOT NULL AUTO_INCREMENT,
  `Nom_liste` varchar(20) NOT NULL,
  PRIMARY KEY (`ID_listeDiff`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `listediffusion`
--

INSERT INTO `listediffusion` (`ID_listeDiff`, `Nom_liste`) VALUES
(1, 'liste1'),
(2, 'azerty'),
(3, 'asc2'),
(5, 'essai2'),
(7, 'ouiiiiiiiiiii7'),
(8, 'user8');

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
  `Anciennete` date NOT NULL,
  `Mail` varchar(100) NOT NULL,
  `Telephone` varchar(10) NOT NULL,
  PRIMARY KEY (`ID_locataire`),
  UNIQUE KEY `ID_Locataire_IND` (`ID_locataire`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `locataire`
--

INSERT INTO `locataire` (`ID_locataire`, `Nom`, `Prenom`, `Age`, `Anciennete`, `Mail`, `Telephone`) VALUES
(1, 'Bobie', 'Williams', 18, '2022-05-06', 'benjaminrandazzo2@hotmail.fr', '0771773740'),
(2, 'paul', 'jacques', 85, '2022-05-04', 'pj@gmail.com', '0369874521'),
(3, 'monsieur', 'prenom', 45, '2019-05-15', 'mp@gmail.com', '0654789321');

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
(1, 3),
(5, 1),
(5, 2),
(0, 1),
(0, 3),
(7, 1),
(7, 2);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
(14, 1),
(13, 2),
(14, 2),
(13, 3),
(15, 3),
(16, 3),
(14, 5),
(19, 5),
(15, 7),
(16, 7),
(14, 8),
(15, 8),
(17, 8),
(19, 8);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `ID_utilisateur` int(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(15) NOT NULL,
  `Mdp` varchar(10) NOT NULL,
  `CAT` char(5) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_utilisateur`),
  UNIQUE KEY `ID_Utilisateur_IND` (`ID_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`ID_utilisateur`, `login`, `Mdp`, `CAT`, `Email`, `Password`) VALUES
(1, 'ben', 'ben', 'adm', '', ''),
(2, 'Benjamin', 'ben', 'adm', 'theluffydu30@gmail.com', 'bvogzfkcpurcwnnc'),
(3, 'admin', 'admin', 'adm', 'test', 'test');

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
(0, 1),
(0, 2),
(0, 3),
(8, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
