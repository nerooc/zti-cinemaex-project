import React, { useEffect, useState } from 'react';
import axios from '../../utils/axios';
import styled from 'styled-components';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { toastConfig } from '../../constants/toastConfig';
import { useAsync } from '../../hooks/useAsync';
import Loading from '../common/Loading';

toast.configure();

const Header = styled.h1`
  text-align: center;
  padding: 30px;
`;

const Container = styled.div`
  display: flex;
  flex-flow: column;
  width: 50%;
  margin: auto;

  @media (max-width: 1050px) {
    width: 80%;
  }
`;

const Select = styled.select`
  height: 42px;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f6f6f6;
  border: 1px solid #c5c5c5;
  border-radius: 10px;
  font-family: Quicksand;
  font-size: 15px;
  font-weight: bold;
`;

const Button = styled.button`
  width: 180px;
  background-color: #5a38fd;
  color: white;
  border: none;
  padding: 15px 20px;
  margin: auto;
  margin-top: 30px;
  border-radius: 25px;
  font-family: 'Quicksand';
  font-size: 16px;
  font-weight: bold;
  transition: 0.3s;

  &:hover {
    opacity: 0.5;
    cursor: pointer;
  }
`;

interface ActorName {
  id: number;
  name: string;
  surname: string;
}

interface ActorNames extends Array<ActorName> {}

const ActorsPanel = () => {
  const [actor, setActor] = useState('1');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setActor(e.target.value);
  };

  const deleteActor = async (id) => {
    try {
      const { data } = await axios.post(`/actors/delete/${id}`, {
        headers: {
          token: localStorage.token,
        },
      });

      if (data) {
        toast.success(data.message, toastConfig);
        execute();
      }
    } catch (err) {
      toast.error(err.data, toastConfig);
    }
  };

  const registerActors = (): Promise<ActorNames> => {
    return new Promise((resolve, reject) => {
      axios
        .get('/actors/', {
          headers: {
            token: localStorage.token,
          },
        })
        .then(({ data }) => {resolve(data); setActor(data[0].id)})
        .catch((err) => reject(err));
    });
  };

  const { status, value, error, execute } = useAsync<ActorNames>(
    registerActors
  );

  return (
    <>
      {status === 'pending' && (
        <Loading type="spinningBubbles" color="#5A38FD" />
      )}
      {status === 'success' && value?.length !== 0 && (
        <Container>
          <Header>Delete an actor</Header>
          <Select
            name="actor"
            /* @ts-ignore */
            onChange={handleChange}
            value={actor}
          >
            {value?.map(({ id, name, surname }) => {
              return (
                <option key={id} value={id}>
                  {name} {surname}
                </option>
              );
            })}
          </Select>

          <Button onClick={() => deleteActor(actor)}>Delete an actor</Button>
        </Container>
      )}
      {status === 'success' && value?.length === 0 && (
        <div style={{textAlign: 'center', padding: '50px 0px'}}>No actors available to delete!</div>
      )}
      {status === 'error' && <div>{error}</div>}
    </>
  );
};

export default ActorsPanel;
