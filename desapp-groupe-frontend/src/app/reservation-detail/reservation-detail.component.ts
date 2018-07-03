import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ReservationService } from '../services/reservation.service';
import { AuthenticationService } from '../services/auth.service';
import { NotificationService } from '../services/notification.service';


@Component({
  selector: 'app-reservation-detail',
  templateUrl: './reservation-detail.component.html',
  styleUrls: ['./reservation-detail.component.css']
})
export class ReservationDetailComponent implements OnInit {

	reservation
	statesInfo
	progress
	progressStyle
	stateProgressInfo
	constructor(private activatedRoute: ActivatedRoute,
				public reservationService: ReservationService,
				public authenticationService: AuthenticationService,
				private notificationService: NotificationService) { 
		this.statesInfo = {
			ReservationNotConfirmedState:"Esperando la confirmacion del dueño",
			ReservationConfirmedState:"El dueño acepto la reserva",
			RetireConfirmedByClientState:"El cliente confirmo el retiro del vehiculo",
			RetireConfirmedByOwnerState:"El dueño confirmo el retiro del vehiculo",
			RetireConfirmedState:"El vehiculo fue retirado",
			ReturnConfirmedByClientState:"El cliente confirmo el retorno del vehiculo",
			ReturnConfirmedByOwnerState:"El dueño confirmo el retorno del vehiculo",
			ReturnConfirmedState:"El vehiculo fue devuelto con exito, alquiler terminado",
		}

		this.stateProgressInfo = {
			ReservationNotConfirmedState:0,
			ReservationConfirmedState:20,
			RetireConfirmedByClientState:40,
			RetireConfirmedByOwnerState:40,
			RetireConfirmedState:40,
			ReturnConfirmedByClientState:80,
			ReturnConfirmedByOwnerState:80,
			ReturnConfirmedState:100,
		}

		this.progressStyle = {'width':'0%'}

	}

	ngOnInit() {
		this.activatedRoute.params.subscribe(params => {
			let idReservation = params.id;
			this.getReservation(idReservation)
		});
	} 

	getStateInfo(){
		return this.statesInfo[this.reservation.state.name];
	}

	refreshProgressStyle(){
		this.progressStyle = {'width': this.stateProgressInfo[this.reservation.state.name]+'%'}
	}

	isReservationMakedByLoguedUser(){
		return this.reservation.publication.owner.email === this.authenticationService.getUserLoggedIn().email
	}

	confirmReservationByOwner(){
		this.reservationService.confirmReservationByOwner(this.reservation.id).subscribe(data => {
			this.getReservation(this.reservation.id);
			this.progress = this.progress + 20;
			this.refreshProgressStyle();
			this.notificationService.onSuccess("Ok","");
		});
	}

	confirmRetireByClient(){
		this.reservationService.confirmRetireByClient(this.reservation.id).subscribe(data => {
			this.getReservation(this.reservation.id);
			this.progress = this.progress + 20;
			this.refreshProgressStyle();	
			this.notificationService.onSuccess("Ok","");			
		});
	}

	confirmReturnByClient(){
		this.reservationService.confirmReturnByClient(this.reservation.id).subscribe(data => {
			this.getReservation(this.reservation.id);
			this.progress = this.progress + 20;
			this.refreshProgressStyle();
			this.notificationService.onSuccess("Ok","");				
		});
	}

	confirmRetireByOwner(){
		this.reservationService.confirmRetireByOwner(this.reservation.id).subscribe(data => {
			this.getReservation(this.reservation.id);
			this.progress = this.progress + 20;
			this.refreshProgressStyle();
			this.notificationService.onSuccess("Ok","");
		});
	}

	confirmReturnByOwner(){
		this.reservationService.confirmReturnByOwner(this.reservation.id).subscribe(data => {
			this.getReservation(this.reservation.id);
			this.progress = this.progress + 20;
			this.refreshProgressStyle();
			this.notificationService.onSuccess("Ok","");
		});
	}

	getReservation(id){
		this.reservationService.getReservation(id).subscribe(reservation => {
			this.reservation = this.reservationsWithDatesFormatted(reservation);
			this.refreshProgressStyle();
			console.log(reservation);
		});
	}


	canConfirmReservationByOwner():boolean{
		return this.reservation.state.order === 0; 
	}

	canConfirmRetireByClient():boolean{
		return this.reservation.state.name !== "RetireConfirmedByClientState" && (this.reservation.state.name === "RetireConfirmedByOwnerState"
				|| (this.reservation.state.order <= 2 && this.reservation.state.order >= 1));  
	}

	canConfirmRetireByOwner():boolean{
		return this.reservation.state.name !== "RetireConfirmedByOwnerState" && (this.reservation.state.name === "RetireConfirmedByClientState"
				|| (this.reservation.state.order <= 2 && this.reservation.state.order >= 1));
	}

	canConfirmReturnByClient():boolean{
		//return this.reservation.state.order === 3 || this.reservation.state.order === 4;
		return this.reservation.state.name !== "ReturnConfirmedByClientState" && (this.reservation.state.name === "ReturnConfirmedByOwnerState"
				|| (this.reservation.state.order <= 4 && this.reservation.state.order >= 3)); 
	}

	canConfirmReturnByOwner():boolean{
		//return this.reservation.state.order === 3 || this.reservation.state.order === 4;
		return this.reservation.state.name !== "ReturnConfirmedByOwnerState" && (this.reservation.state.name === "ReturnConfirmedByClientState"
				|| (this.reservation.state.order <= 4 && this.reservation.state.order >= 3));
	}


	reservationsWithDatesFormatted(reservation){
		let fromDate = reservation.fromDate;
		let toDate = reservation.toDate;
		reservation.fromDate = fromDate.year + "-" + fromDate.monthOfYear + "-" + fromDate.dayOfMonth;
		reservation.toDate = toDate.year + "-" + toDate.monthOfYear + "-" + toDate.dayOfMonth;

		return reservation;	
	}

}
