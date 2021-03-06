import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Publication } from '../../model/publication';
import { Reservation } from '../../model/reservation';

@Injectable()
export class ReservationService {

  url:string = '/rest/reservations/';

  constructor(private httpClient: HttpClient) { }

  getReservationsByEmail(email):Observable<any> {
    return this.httpClient.get<Reservation[]>(this.url +'byemail/' + email);
  }

  getReservationsOfPublicationsByEmail(email){
    return this.httpClient.get<Reservation[]>(this.url +'reservationsOfPublicationsByemail/' + email);
  }

  getReservation(id):Observable<any> {
    return this.httpClient.get(this.url+id);
  }

  confirmReservationByOwner(id){
    return this.httpClient.put(this.url + 'confirmReservationByOwner/', id);
  }

  confirmRetireByClient(id){
    return this.httpClient.put(this.url + 'confirmRetireByClient/', id);
  }

  confirmReturnByClient(id){
    return this.httpClient.put(this.url + 'confirmReturnByClient/', id);
  }

  confirmRetireByOwner(id){
    return this.httpClient.put(this.url + 'confirmRetireByOwner/', id);
  }

  confirmReturnByOwner(id){
    return this.httpClient.put(this.url + 'confirmReturnByOwner/', id);
  }
/*
  newPublication(publication):Observable<any> {
    return this.httpClient.post(this.url+'new',publication)
  }

  updatePublication(id, publication):Observable<any> {
    return this.httpClient.put(this.url+'edit/'+id,publication)
  }

  deletePublication(id):Observable<any> {
    return this.httpClient.delete(this.url+id)
  }

  filterPublications(term):Observable<any> {
    return this.httpClient.get(this.url + 'find/' + term);
  }

  newReservation(reservation:Reservation, idPublication):Observable<any> {
    return this.httpClient.put(this.url + idPublication + '/newReservation', reservation);
  }   */

}