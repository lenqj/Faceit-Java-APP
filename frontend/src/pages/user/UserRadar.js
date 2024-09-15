import React, { Component } from 'react';
import Chart from 'chart.js/auto';

class UserRadar extends Component {
  constructor(props) {
    super(props);
    this.chartRef = React.createRef();
    this.chartInstance = null;
  }

  componentDidMount() {
    this.createRadarChart();
  }

  componentDidUpdate(prevProps) {
    if (prevProps.result !== this.props.result || prevProps.showFields !== this.props.showFields) {
      this.createRadarChart();
    }
  }

  createRadarChart = () => {
    const { result, showFields } = this.props;

    if (!result || !result.player1 || !result.player2) return;

    const player1 = result.player1.score;
    const player2 = result.player2.score;
    const labels = Object.keys(player1);
    const player1Data = Object.values(player1).filter((_, index) => showFields[labels[index]]);
    const player2Data = Object.values(player2).filter((_, index) => showFields[labels[index]]);

    const ctx = this.chartRef.current.getContext('2d');

    if (this.chartInstance) {
      this.chartInstance.destroy();
    }

    this.chartInstance = new Chart(ctx, {
      type: 'radar',
      data: {
        labels: labels.filter(label => showFields[label]),
        datasets: [{
          label: result.player1.username,
          data: player1Data,
          backgroundColor: 'rgba(255, 99, 132, 0.5)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 2
        }, {
          label: result.player2.username,
          data: player2Data,
          backgroundColor: 'rgba(54, 162, 235, 0.5)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 2
        }]
      },
      options: {
        scales: {
          r: {
            suggestedMin: 0,
            suggestedMax: 10,
            grid: {
              color: 'rgba(0, 0, 0, 0.2)'
            },
            angleLines: {
              color: 'rgba(0, 0, 0, 0.2)'
            },
            pointLabels: {
              fontColor: 'rgba(0, 0, 0, 0.7)'
            }
          }
        },
        plugins: {
          legend: {
            labels: {
              fontColor: 'rgba(0, 0, 0, 0.7)'
            }
          }
        }
      }
    });
  };

  render() {
    return (
      <canvas ref={this.chartRef}/>
    );
  }
}

export default UserRadar;
