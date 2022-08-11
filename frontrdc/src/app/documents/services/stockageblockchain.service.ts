import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
//const Web3 = require('web3');
let  contract = require('@truffle/contract');
const tokenAbi = require('../model/DocumentHash.json');
declare let window: any;

@Injectable({
  providedIn: 'root'
})
export class StockageblockchainService {
   
  readonly api = environment.apii;
  private web3Provider;
  private contracts: {};
  account
  constructor(private http: HttpClient) { 
   
    /*if (typeof window.web3 !== 'undefined') {
      window.ethereum.enable()
      //this.web3Provider = window.web3.currentProvider;
    } else {
      //this.web3Provider = new Web3.providers.HttpProvider('http://127.0.0.1:7545');
 
    }
 
    window.web3 = new Web3(this.web3Provider);*/
  }

  saveBlockchain(doc,file,username){
    let formData = new FormData();
    formData.append('file',file);
    formData.append('document',JSON.stringify(doc));
    formData.append('username',username);
    return this.http.post<any>(`${this.api}stockageblockchain/add`,formData);
  }

  deleteFileOnBd(hash){
    return this.http.get<any>(`${this.api}stockageblockchain/delete/${hash}`);
  }

  getListDoc(username){
    return this.http.get<any>(`${this.api}stockageblockchain/list/${username}`);
  }

   saveOnBlockchainNetwork(hash,nomfile,account,username){
  let that = this;
 

  return new Promise((resolve, reject) => {
    //  let docContrat = contract(tokenAbi);
    // docContrat.setProvider(that.web3Provider);

    //return new Promise((resolve, reject) => {
      /*let docContrat = contract(tokenAbi);
      docContrat.setProvider(that.web3Provider);

 
      docContrat.deployed().then(function(instance) {
        return instance.addDocHash(
           hash,
       nomfile,
           username,
         {
             from:account
         });
      }).then(function(status) {
        if(status) {
         return resolve(status);
        }
       }).catch(function(error){
       console.log(error);
          return reject(`${error.code}`);
 
          // return reject("Error in transferEther service call");
        });*/
  });

      }

 async verifyOnBlockchainNetwork(hash){
   //let that = this;
 
   //return new Promise((resolve, reject) => {
      /*let docContrat = contract(tokenAbi);
  //     docContrat.setProvider(that.web3Provider);
 
  // //     docContrat.deployed().then(function(instance) {
  // //         return instance.findDocHash(hash);
  // //       }).then(function(status) {
  // //         if(status) {
  // //           return resolve(status);
  // //         }
  // //       }).catch(function(error){
  // //         console.log(error);
 
  //         return reject("Error in transferEther service call");
  //       });*/
  //   }
  //   );
    
}

  getAccountInfo() {
    return new Promise((resolve, reject) => {
      /*window.web3.eth.getCoinbase(function(err, account) {
 
        if(err === null) {
          window.web3.eth.getBalance(account, function(err, balance) {
            if(err === null) {
              return resolve({fromAccount: account});
            } else {
              return reject({fromAccount: "error"});
            }
          });
        }
      });*/
    });
  }
}
