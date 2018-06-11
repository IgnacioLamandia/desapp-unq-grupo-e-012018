import { SocialUser } from "angularx-social-login";
export class User {

    public id:number;
    public cuil:string;
    public name:string ;
    public surname:string;
    public address:string ;
    public email:string ;
    public haveFullProfile:boolean;
    
    constructor(
        cuil?: string,
        name?: string,
        surname?: string,
        address?: string,
        email?: string,
        id?:number)	{
  
        this.id = id;
        this.cuil = cuil
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.haveFullProfile = false;
    }

    fromSocialUser(socialUser:SocialUser){
        this.name = socialUser.firstName;
        this.surname = socialUser.lastName;
        this.email = socialUser.email;
        this.haveFullProfile = false;

        return this;
    }

    setHaveFullProfile(boolean:boolean){
        this.haveFullProfile = boolean;
    }

    getHaveFullProfile(){
        return this.haveFullProfile;
    }
  
  }