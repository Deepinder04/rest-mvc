package project.first.spring.fileConversion.model;


import lombok.Builder;
import project.first.spring.fileConversion.model.personalisation.*;

@Builder
public class UserDateWiseMfNav {

    Double nav;
    Double roi;
    Double currentInvestment;
    Double goldWeight;
    AAPersonalisationData bankAccount;
    MfPersonalisationData mutualfund;
    GoldPersonalisationData gold;
    EpfoPersonalisationData epfo;
    P2PPersonalisationData p2p;
    FdPersonalisationData fd;

    public Double getNav() {
        return nav;
    }

    public void setNav(Double nav) {
        this.nav = nav;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double xirr) {
        this.roi = xirr;
    }

    public Double getCurrentInvestment() {return currentInvestment;}

    public void setCurrentInvestment(Double currentInvestment) {
        this.currentInvestment = currentInvestment;
    }

    public Double getGoldWeight() {
        return goldWeight;
    }

    public void setGoldWeight(Double goldWeight) {
        this.goldWeight = goldWeight;
    }

    public AAPersonalisationData getBankAccount(){
        return this.bankAccount;
    }

    public void setBankAccount(AAPersonalisationData aAPersonalisationData){
        this.bankAccount = aAPersonalisationData;
    }

    public GoldPersonalisationData getGold(){
        return this.gold;
    }

    public void setGold(GoldPersonalisationData gold){
        this.gold = gold;
    }

    public MfPersonalisationData getMutualfund(){
        return this.mutualfund;
    }

    public void setMutualfund(MfPersonalisationData mutualfund){
        this.mutualfund = mutualfund;
    }

    public EpfoPersonalisationData getEpfo(){
        return this.epfo;
    }

    public void setEpfo(EpfoPersonalisationData epfo){
        this.epfo = epfo;
    }

    public P2PPersonalisationData getP2p(){
        return this.p2p;
    }

    public void setP2p(P2PPersonalisationData personalisationData){
        this.p2p = personalisationData;
    }

    public FdPersonalisationData getFd(){
        return this.fd;
    }

    public void setFd(FdPersonalisationData fd){
        this.fd = fd;
    }
}
