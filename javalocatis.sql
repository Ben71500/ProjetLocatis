-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 25 fév. 2022 à 13:20
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
-- Structure de la table `appartement`
--

DROP TABLE IF EXISTS `appartement`;
CREATE TABLE IF NOT EXISTS `appartement` (
  `ID_batiment` int(10) NOT NULL AUTO_INCREMENT,
  `NumeroAppartement` char(5) COLLATE utf8_bin NOT NULL,
  `NombreEtage` char(2) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_batiment`),
  UNIQUE KEY `ID_Appar_Batim_IND` (`ID_batiment`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `appartement`
--

INSERT INTO `appartement` (`ID_batiment`, `NumeroAppartement`, `NombreEtage`) VALUES
(1, '30', '15');

-- --------------------------------------------------------

--
-- Structure de la table `batiment`
--

DROP TABLE IF EXISTS `batiment`;
CREATE TABLE IF NOT EXISTS `batiment` (
  `ID_batiment` int(10) NOT NULL,
  `Adresse` char(25) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_batiment`),
  UNIQUE KEY `ID_Batiment_IND` (`ID_batiment`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `batiment`
--

INSERT INTO `batiment` (`ID_batiment`, `Adresse`) VALUES
(0, '3A rue des Serpents'),
(1, '3 rue des Nains');

-- --------------------------------------------------------

--
-- Structure de la table `campagne`
--

DROP TABLE IF EXISTS `campagne`;
CREATE TABLE IF NOT EXISTS `campagne` (
  `ID_campagne` int(64) NOT NULL AUTO_INCREMENT,
  `Date_Debut` date NOT NULL,
  `Date_Fin` date NOT NULL,
  `frequence` decimal(10,0) NOT NULL,
  `ID_utilisateur` decimal(20,0) NOT NULL,
  PRIMARY KEY (`ID_campagne`),
  UNIQUE KEY `ID_Campagne_IND` (`ID_campagne`),
  KEY `REF_Campa_Utili_IND` (`ID_utilisateur`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `contient`
--

DROP TABLE IF EXISTS `contient`;
CREATE TABLE IF NOT EXISTS `contient` (
  `ID_campagne` int(64) NOT NULL AUTO_INCREMENT,
  `ID_message` int(60) NOT NULL,
  PRIMARY KEY (`ID_message`,`ID_campagne`),
  UNIQUE KEY `ID_CONTIENT_IND` (`ID_message`,`ID_campagne`),
  KEY `REF_CONTI_Campa_IND` (`ID_campagne`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `habiter`
--

DROP TABLE IF EXISTS `habiter`;
CREATE TABLE IF NOT EXISTS `habiter` (
  `ID_batiment` int(10) NOT NULL AUTO_INCREMENT,
  `ID_locataire` decimal(40,0) NOT NULL,
  PRIMARY KEY (`ID_batiment`,`ID_locataire`),
  UNIQUE KEY `ID_HABITER_IND` (`ID_batiment`,`ID_locataire`),
  KEY `EQU_HABIT_Locat_IND` (`ID_locataire`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `locataire`
--

DROP TABLE IF EXISTS `locataire`;
CREATE TABLE IF NOT EXISTS `locataire` (
  `ID_locataire` int(40) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(20) COLLATE utf8_bin NOT NULL,
  `Prenom` varchar(20) COLLATE utf8_bin NOT NULL,
  `Age` decimal(3,0) NOT NULL,
  `Anciennete` date NOT NULL,
  `Mail` varchar(100) COLLATE utf8_bin NOT NULL,
  `Telephone` varchar(10) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_locataire`),
  UNIQUE KEY `ID_Locataire_IND` (`ID_locataire`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `maison`
--

DROP TABLE IF EXISTS `maison`;
CREATE TABLE IF NOT EXISTS `maison` (
  `ID_batiment` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID_batiment`),
  UNIQUE KEY `ID_Maiso_Batim_IND` (`ID_batiment`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `maison`
--

INSERT INTO `maison` (`ID_batiment`) VALUES
(1);

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `ID_message` int(60) NOT NULL AUTO_INCREMENT,
  `Contenu` varchar(64) COLLATE utf8_bin NOT NULL,
  `Date_Ecriture` date NOT NULL,
  PRIMARY KEY (`ID_message`),
  UNIQUE KEY `ID_Message_IND` (`ID_message`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `recevoir`
--

DROP TABLE IF EXISTS `recevoir`;
CREATE TABLE IF NOT EXISTS `recevoir` (
  `ID_campagne` int(64) NOT NULL AUTO_INCREMENT,
  `ID_locataire` decimal(40,0) NOT NULL,
  PRIMARY KEY (`ID_locataire`,`ID_campagne`),
  UNIQUE KEY `ID_RECEVOIR_IND` (`ID_locataire`,`ID_campagne`),
  KEY `REF_RECEV_Campa_IND` (`ID_campagne`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `ID_utilisateur` int(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(10) COLLATE utf8_bin NOT NULL,
  `Mdp` varchar(15) COLLATE utf8_bin NOT NULL,
  `CAT` char(3) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_utilisateur`),
  UNIQUE KEY `ID_Utilisateur_IND` (`ID_utilisateur`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`ID_utilisateur`, `login`, `Mdp`, `CAT`) VALUES
(1, 'math01', 'Mathilde', 'ges');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
