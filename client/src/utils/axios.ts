import axios from 'axios';

const instance = axios.create({
  baseURL: 'https://cinemaex-java.herokuapp.com/api',
});

export default instance;
