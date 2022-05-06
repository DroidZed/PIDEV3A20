<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220424084135 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE tbl_product ADD image VARCHAR(255) NOT NULL, ADD updated_at DATETIME NOT NULL, CHANGE nameProduct nameproduct VARCHAR(255) NOT NULL, CHANGE idCategory idCategory INT DEFAULT NULL, CHANGE idUser idUser INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tbl_product ADD CONSTRAINT FK_88190CD955EF339A FOREIGN KEY (idCategory) REFERENCES tbl_category (idCategory)');
        $this->addSql('ALTER TABLE tbl_product ADD CONSTRAINT FK_88190CD9FE6E88D7 FOREIGN KEY (idUser) REFERENCES tbl_user (idUser)');
        $this->addSql('ALTER TABLE tbl_promo DROP FOREIGN KEY fk_promo_product');
        $this->addSql('ALTER TABLE tbl_promo CHANGE idProduct idProduct INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tbl_promo ADD CONSTRAINT FK_1E77D0E4C3F36F5F FOREIGN KEY (idProduct) REFERENCES tbl_product (idProduct)');
        $this->addSql('ALTER TABLE tbl_publication DROP FOREIGN KEY fk_pub_Vg');
        $this->addSql('ALTER TABLE tbl_publication CHANGE idGV idGV INT DEFAULT NULL, CHANGE nbrjaime nbrjaime DOUBLE PRECISION NOT NULL');
        $this->addSql('ALTER TABLE tbl_publication ADD CONSTRAINT FK_285D87E11584296A FOREIGN KEY (idGV) REFERENCES tbl_videogame (id)');
        $this->addSql('ALTER TABLE tbl_reaction DROP FOREIGN KEY fk_reaction_post');
        $this->addSql('ALTER TABLE tbl_reaction DROP FOREIGN KEY fk_reaction_user');
        $this->addSql('ALTER TABLE tbl_reaction DROP FOREIGN KEY fk_reaction_comment');
        $this->addSql('ALTER TABLE tbl_reaction DROP FOREIGN KEY fk_reaction_tyReact');
        $this->addSql('ALTER TABLE tbl_reaction CHANGE idTypeReact idTypeReact INT DEFAULT NULL, CHANGE idUser idUser INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tbl_reaction ADD CONSTRAINT FK_F3E4E1DAFE6E88D7 FOREIGN KEY (idUser) REFERENCES tbl_user (idUser)');
        $this->addSql('ALTER TABLE tbl_reaction ADD CONSTRAINT FK_F3E4E1DA84CD399E FOREIGN KEY (idComment) REFERENCES tbl_comment (idComment)');
        $this->addSql('ALTER TABLE tbl_reaction ADD CONSTRAINT FK_F3E4E1DAAE64DE94 FOREIGN KEY (idTypeReact) REFERENCES tbl_typereact (idTypeReact)');
        $this->addSql('ALTER TABLE tbl_reaction ADD CONSTRAINT FK_F3E4E1DA29773213 FOREIGN KEY (idPost) REFERENCES tbl_post (idPost)');
        $this->addSql('ALTER TABLE tbl_reclamation CHANGE idUser idUser INT DEFAULT NULL, CHANGE typeComplaint typeComplaint INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tbl_stateuser CHANGE idStateUser idStateUser INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE tbl_streaming CHANGE etat etat INT NOT NULL');
        $this->addSql('ALTER TABLE tbl_user DROP FOREIGN KEY fk_user_stateUser');
        $this->addSql('ALTER TABLE tbl_user DROP FOREIGN KEY fk_badge_user');
        $this->addSql('ALTER TABLE tbl_user CHANGE stateUser stateUser INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tbl_user ADD CONSTRAINT FK_38B383A1E9340B00 FOREIGN KEY (idBadge) REFERENCES tbl_badge (idBadge)');
        $this->addSql('ALTER TABLE tbl_user ADD CONSTRAINT FK_38B383A1E7782204 FOREIGN KEY (stateUser) REFERENCES tbl_stateuser (idStateUser)');
        $this->addSql('ALTER TABLE tbl_userorder DROP FOREIGN KEY fk_userOrder_statusOrder');
        $this->addSql('ALTER TABLE tbl_userorder DROP FOREIGN KEY fk_userOrder_payType');
        $this->addSql('ALTER TABLE tbl_userorder DROP FOREIGN KEY fk_userOrder_user');
        $this->addSql('ALTER TABLE tbl_userorder CHANGE idStatusOrder idStatusOrder INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tbl_userorder ADD CONSTRAINT FK_9705C0D95B7F1DED FOREIGN KEY (idPayType) REFERENCES tbl_paytype (idPayType)');
        $this->addSql('ALTER TABLE tbl_userorder ADD CONSTRAINT FK_9705C0D9FE6E88D7 FOREIGN KEY (idUser) REFERENCES tbl_user (idUser)');
        $this->addSql('ALTER TABLE tbl_userorder ADD CONSTRAINT FK_9705C0D911969E6B FOREIGN KEY (idStatusOrder) REFERENCES tbl_statusorder (idStatusOrder)');
        $this->addSql('ALTER TABLE tbl_videogame CHANGE rating rating INT NOT NULL, CHANGE likes likes INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tbl_wishlist DROP FOREIGN KEY fk_wishlist_user');
        $this->addSql('ALTER TABLE tbl_wishlist DROP FOREIGN KEY fk_wishlist_product');
        $this->addSql('ALTER TABLE tbl_wishlist CHANGE idUser idUser INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tbl_wishlist ADD CONSTRAINT FK_CBD2CC1CC3F36F5F FOREIGN KEY (idProduct) REFERENCES tbl_product (idProduct)');
        $this->addSql('ALTER TABLE tbl_wishlist ADD CONSTRAINT FK_CBD2CC1CFE6E88D7 FOREIGN KEY (idUser) REFERENCES tbl_user (idUser)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE tbl_answerpost CHANGE answer answer VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_badge CHANGE nameBadge nameBadge VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE descBadge descBadge VARCHAR(100) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE photoBadge photoBadge VARCHAR(100) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_candidacy CHANGE etat etat VARCHAR(20) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE imageCV imageCV VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_cardtype CHANGE cardType cardType VARCHAR(8) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_category CHANGE nameCategory nameCategory VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_comment CHANGE comment comment VARCHAR(300) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_commentaire CHANGE descriptionCom descriptionCom VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_domain CHANGE name name VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE description description VARCHAR(100) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_offer CHANGE titleOffer titleOffer VARCHAR(30) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE TypeOffer TypeOffer VARCHAR(20) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE descOffer descOffer VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE regionOffer regionOffer VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE addressOffer addressOffer VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_option CHANGE contentOption contentOption VARCHAR(150) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_passage CHANGE resultatPassage resultatPassage VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_paytype CHANGE payType payType VARCHAR(15) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_post CHANGE titlePost titlePost VARCHAR(100) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE descPost descPost VARCHAR(500) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE photoPost photoPost VARCHAR(200) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE typePost typePost VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE addressEvent addressEvent VARCHAR(150) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE correctAnswer correctAnswer VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_product DROP FOREIGN KEY FK_88190CD955EF339A');
        $this->addSql('ALTER TABLE tbl_product DROP FOREIGN KEY FK_88190CD9FE6E88D7');
        $this->addSql('ALTER TABLE tbl_product DROP image, DROP updated_at, CHANGE nameproduct nameProduct VARCHAR(100) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE imageProduct imageProduct VARCHAR(150) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE idCategory idCategory INT NOT NULL, CHANGE idUser idUser INT NOT NULL');
        $this->addSql('ALTER TABLE tbl_promo DROP FOREIGN KEY FK_1E77D0E4C3F36F5F');
        $this->addSql('ALTER TABLE tbl_promo CHANGE codePromo codePromo VARCHAR(150) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE idProduct idProduct INT NOT NULL');
        $this->addSql('ALTER TABLE tbl_promo ADD CONSTRAINT fk_promo_product FOREIGN KEY (idProduct) REFERENCES tbl_product (idProduct) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_publication DROP FOREIGN KEY FK_285D87E11584296A');
        $this->addSql('ALTER TABLE tbl_publication CHANGE descriptionPub descriptionPub VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE nbrjaime nbrjaime DOUBLE PRECISION DEFAULT \'0\' NOT NULL, CHANGE idGV idGV INT NOT NULL');
        $this->addSql('ALTER TABLE tbl_publication ADD CONSTRAINT fk_pub_Vg FOREIGN KEY (idGV) REFERENCES tbl_videogame (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_rate CHANGE name name VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_reaction DROP FOREIGN KEY FK_F3E4E1DAFE6E88D7');
        $this->addSql('ALTER TABLE tbl_reaction DROP FOREIGN KEY FK_F3E4E1DA84CD399E');
        $this->addSql('ALTER TABLE tbl_reaction DROP FOREIGN KEY FK_F3E4E1DAAE64DE94');
        $this->addSql('ALTER TABLE tbl_reaction DROP FOREIGN KEY FK_F3E4E1DA29773213');
        $this->addSql('ALTER TABLE tbl_reaction CHANGE idUser idUser INT NOT NULL, CHANGE idTypeReact idTypeReact INT NOT NULL');
        $this->addSql('ALTER TABLE tbl_reaction ADD CONSTRAINT fk_reaction_post FOREIGN KEY (idPost) REFERENCES tbl_post (idPost) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_reaction ADD CONSTRAINT fk_reaction_user FOREIGN KEY (idUser) REFERENCES tbl_user (idUser) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_reaction ADD CONSTRAINT fk_reaction_comment FOREIGN KEY (idComment) REFERENCES tbl_comment (idComment) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_reaction ADD CONSTRAINT fk_reaction_tyReact FOREIGN KEY (idTypeReact) REFERENCES tbl_typereact (idTypeReact) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_reclamation CHANGE message message VARCHAR(500) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE statusComplaint statusComplaint VARCHAR(30) DEFAULT \'NON TRAITEE\' NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE answerComplaint answerComplaint VARCHAR(500) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE idUser idUser INT NOT NULL, CHANGE typeComplaint typeComplaint INT NOT NULL');
        $this->addSql('ALTER TABLE tbl_stateuser CHANGE idStateUser idStateUser INT NOT NULL, CHANGE name name VARCHAR(11) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_statusorder CHANGE statusOrder statusOrder VARCHAR(11) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_streaming CHANGE link link VARCHAR(250) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE description description VARCHAR(250) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE etat etat INT DEFAULT 0 NOT NULL');
        $this->addSql('ALTER TABLE tbl_test CHANGE durationTest durationTest VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE questionTest questionTest VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE choix choix VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE question question VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_typecomplaint CHANGE nameType nameType VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_typeoffer CHANGE typeOffer typeOffer VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_typereact CHANGE typeReact typeReact VARCHAR(10) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE iconReact iconReact VARCHAR(100) NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE tbl_user DROP FOREIGN KEY FK_38B383A1E9340B00');
        $this->addSql('ALTER TABLE tbl_user DROP FOREIGN KEY FK_38B383A1E7782204');
        $this->addSql('ALTER TABLE tbl_user CHANGE nameUser nameUser VARCHAR(100) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE emailUser emailUser VARCHAR(50) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE pwdUser pwdUser VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE phone phone VARCHAR(8) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE photoUser photoUser VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE roleUser roleUser VARCHAR(11) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE cv cv VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE descUser descUser VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE stateUser stateUser INT DEFAULT 0 NOT NULL');
        $this->addSql('ALTER TABLE tbl_user ADD CONSTRAINT fk_user_stateUser FOREIGN KEY (stateUser) REFERENCES tbl_stateuser (idStateUser) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_user ADD CONSTRAINT fk_badge_user FOREIGN KEY (idBadge) REFERENCES tbl_badge (idBadge) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_userorder DROP FOREIGN KEY FK_9705C0D95B7F1DED');
        $this->addSql('ALTER TABLE tbl_userorder DROP FOREIGN KEY FK_9705C0D9FE6E88D7');
        $this->addSql('ALTER TABLE tbl_userorder DROP FOREIGN KEY FK_9705C0D911969E6B');
        $this->addSql('ALTER TABLE tbl_userorder CHANGE orderAddress orderAddress VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE idStatusOrder idStatusOrder INT DEFAULT 1 NOT NULL');
        $this->addSql('ALTER TABLE tbl_userorder ADD CONSTRAINT fk_userOrder_statusOrder FOREIGN KEY (idStatusOrder) REFERENCES tbl_statusorder (idStatusOrder) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_userorder ADD CONSTRAINT fk_userOrder_payType FOREIGN KEY (idPayType) REFERENCES tbl_paytype (idPayType) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_userorder ADD CONSTRAINT fk_userOrder_user FOREIGN KEY (idUser) REFERENCES tbl_user (idUser) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_videogame CHANGE nameVg nameVg VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE imageVg imageVg VARCHAR(255) NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE rating rating INT DEFAULT 0 NOT NULL, CHANGE likes likes INT DEFAULT 0');
        $this->addSql('ALTER TABLE tbl_wishlist DROP FOREIGN KEY FK_CBD2CC1CC3F36F5F');
        $this->addSql('ALTER TABLE tbl_wishlist DROP FOREIGN KEY FK_CBD2CC1CFE6E88D7');
        $this->addSql('ALTER TABLE tbl_wishlist CHANGE idUser idUser INT NOT NULL');
        $this->addSql('ALTER TABLE tbl_wishlist ADD CONSTRAINT fk_wishlist_user FOREIGN KEY (idUser) REFERENCES tbl_user (idUser) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tbl_wishlist ADD CONSTRAINT fk_wishlist_product FOREIGN KEY (idProduct) REFERENCES tbl_product (idProduct) ON DELETE CASCADE');
    }
}
